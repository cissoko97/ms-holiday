package org.ck.holiday.securityservice;

import org.ck.holiday.securityservice.component.PropertySourceResolver;
import org.ck.holiday.securityservice.config.RsaKeyConfig;
import org.ck.holiday.securityservice.utils.models.AuthRequest;
import org.ck.holiday.securityservice.web.AuthController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
@EnableConfigurationProperties(RsaKeyConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class SecurityServiceApplicationTests {

    @Mock
    private AuthController authController;

    @Autowired
    private PropertySourceResolver propertySourceResolver;

//    @Test
//    void contextLoads() {
//
//        assertThat(authController).isNotNull();
//    }

//    @Test
//    public void generateToken() {
//        var request = new AuthRequest();
//        request.setUsername("admin");
//        request.setPassword("1234");
//        request.setWithRefreshToken(false);
//        request.setRefreshToken("false");
//
//        ResponseEntity<Map<String, String>> payload = authController.jwtToken(request);
//        assertThat(payload.getBody().get("accessToken")).isNotNull();
//    }
//
//
//    @Test
//    public void saveTest() {
//        assertEquals(propertySourceResolver.getFirstProperty(), "file");
//        assertEquals(propertySourceResolver.getSecondProperty(), "file");
//    }
}
