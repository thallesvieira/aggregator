package com.api.aggregator.domain.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface ITokenService {
    String createJwt(Authentication authentication);
    String retrieve(HttpServletRequest request);
    boolean isValid(String token);
    Long getUserId(String token);
}
