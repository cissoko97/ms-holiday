package org.ck.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class ApiTestController {

    @PostMapping("/ok")
    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.ok()
                .body("Bonjour");
    }

    @GetMapping("/test")
    public ResponseEntity<?> testResponse() {

        Map<String , String> map = new HashMap<>();
        map.put("test" , "bonjour");
        return ResponseEntity.ok()
                .body(map);
    }
}
