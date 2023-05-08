package org.ck.holiday.securityservice.web;

import org.ck.holiday.securityservice.service.AuthService;
import org.ck.holiday.securityservice.service.impl.AuthServiceImpl;
import org.ck.holiday.securityservice.utils.models.AuthRequest;
import org.ck.holiday.securityservice.utils.models.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/token")
    public ResponseEntity<Map<String, String>> jwtToken(@RequestBody AuthRequest request) {
        try {
            return new ResponseEntity<>(authService.authenticate(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/auth/signIn")
    public ResponseEntity<Map<String, String>> signIn(@RequestBody SignUpRequest request) {
        try {
            Map<String, String> map = this.authService.signIn(request);
            return new ResponseEntity<>(map, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
