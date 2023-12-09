package org.ck.holiday.securityservice.web;

import org.ck.holiday.securityservice.service.AuthService;
import org.ck.holiday.securityservice.utils.models.AuthRequest;
import org.ck.holiday.securityservice.utils.models.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.logging.Logger;

@RestController
public class AuthController {
    private final Logger logger = Logger.getLogger(AuthController.class.getName());

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
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/auth/data/{id}")
    public ResponseEntity<Map<String, String>> data(@PathVariable(name = "id") String id) {
        return new ResponseEntity(Map.of("data", "boris", "id", id), HttpStatus.OK);
    }
}
