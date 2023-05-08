package org.ck.holiday.securityservice.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestRestAPI {

    @GetMapping("data/test")
    @SecurityRequirement(name = "Bearer Authentication", scopes = {"USER"})
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public Map<String, Object> dataTest(Authentication authentication) {
        return Map.of("message", "Data test", "username", authentication.getName(), "authorization", authentication.getAuthorities());
    }

    @PostMapping("data/save")
    @SecurityRequirement(name = "Bearer Authentication", scopes = {"ADMIN"})
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Map<String, String> saveData(Authentication authentication, String data) {
        return Map.of("dataSaved", data);
    }

    @Operation(summary = "Api without security")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Juste make test on route",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)})
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testData(HttpServletRequest request) {
        return new ResponseEntity<>(Map.of("dataSaved", "D'accord"), HttpStatus.OK);
    }
}
