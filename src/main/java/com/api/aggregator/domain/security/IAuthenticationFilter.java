package com.api.aggregator.domain.security;

import com.api.aggregator.domain.model.Login;

public interface IAuthenticationFilter {
    String authenticateLogin(Login login);
}
