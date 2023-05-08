package org.ck.holiday.securityservice.service.impl;

import jakarta.transaction.Transactional;
import org.ck.holiday.securityservice.exception.CustomException;
import org.ck.holiday.securityservice.models.ERole;
import org.ck.holiday.securityservice.models.Role;
import org.ck.holiday.securityservice.models.User;
import org.ck.holiday.securityservice.repository.RoleRepository;
import org.ck.holiday.securityservice.repository.UserRepository;
import org.ck.holiday.securityservice.service.AuthService;
import org.ck.holiday.securityservice.utils.models.AuthRequest;
import org.ck.holiday.securityservice.utils.models.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    public static final String ERROR_ROLE_IS_NOT_FOUND = "Error: Role is not found.";
    private final Logger logger = Logger.getLogger(AuthServiceImpl.class.getName());

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Override
    @Transactional
    public Map<String, String> authenticate(AuthRequest request) throws CustomException {
        Authentication authentication = null;
        String subject = null;
        List<String> roles = new ArrayList<>();
        String scope = null;
        String role = null;

        if ("password".equalsIgnoreCase(request.getGrantType())) {
            try {
                authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage());
                throw new CustomException(e.getMessage(), "lang.ref");
            }
            subject = authentication.getName();
            scope = authentication.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(" "));
        } else if ("refreshToken".equals(request.getGrantType())) {
            if (request.getRefreshToken() == null) {
                //TODO throw exception
                return Map.of("errorMessage", "refresh token is required");
            }
            Jwt jwtDecode = null;
            try {
                jwtDecode = jwtDecoder.decode(request.getRefreshToken());

            } catch (Exception e) {
                //TODO throw exception
                return Map.of("errorMessage", e.getMessage());
            }
            subject = jwtDecode.getSubject();

            UserDetails userDetails = userDetailsService.loadUserByUsername(subject);

            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            scope = authorities.stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(" "));
        }

        Map<String, String> idToken = new HashMap<>();
        Instant instant = Instant.now();

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(request.isWithRefreshToken() ? 1 : 5, ChronoUnit.MINUTES))
                .issuer(applicationName)
                .claim("scope", scope)
                .build();

        var jwtAccesToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        idToken.put("accessToken", jwtAccesToken);

        if (request.isWithRefreshToken()) {
            var jwtRefreshClaimSet = JwtClaimsSet.builder().subject(subject).issuedAt(instant).expiresAt(instant.plus(5, ChronoUnit.MINUTES)).issuer(applicationName).build();
            var refreshToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtRefreshClaimSet)).getTokenValue();
            idToken.put("refreshToken", refreshToken);
        }
        return idToken;
    }

    @Override
    @Transactional
    public Map<String, String> signIn(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()));
        user.setAddress(signUpRequest.getAddress());
        user.setPhone(signUpRequest.getPhone());
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.USER).orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByName(ERole.ADMIN).orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND));
                        roles.add(adminRole);
                    }
                    case "mod" -> {
                        Role modRole = roleRepository.findByName(ERole.MODERATOR).orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND));
                        roles.add(modRole);
                    }
                    default -> {
                        Role userRole = roleRepository.findByName(ERole.USER).orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND));
                        roles.add(userRole);
                    }
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return Map.of("Success", "User registered successfully!");
    }
}
