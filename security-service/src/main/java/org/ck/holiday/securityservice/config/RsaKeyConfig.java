package org.ck.holiday.securityservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "rsa", ignoreInvalidFields = true)
public record RsaKeyConfig(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}
