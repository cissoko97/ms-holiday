package org.ck.holiday.securityservice.service;

import jakarta.transaction.Transactional;
import org.ck.holiday.securityservice.exception.CustomException;
import org.ck.holiday.securityservice.utils.models.AuthRequest;
import org.ck.holiday.securityservice.utils.models.SignUpRequest;

import java.util.Map;

public interface AuthService {
    @Transactional
    Map<String, String> authenticate(AuthRequest request) throws CustomException;

    @Transactional
    Map<String, String> signIn(SignUpRequest signUpRequest);
}
