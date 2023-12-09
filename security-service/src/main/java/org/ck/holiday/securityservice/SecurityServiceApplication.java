package org.ck.holiday.securityservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.ck.holiday.securityservice.config.RsaKeyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@OpenAPIDefinition
@EnableConfigurationProperties(RsaKeyConfig.class)
public class SecurityServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityServiceApplication.class, args);
    }

}
