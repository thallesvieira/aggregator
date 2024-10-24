package com.api.aggregator.application.web.controller;

import com.api.aggregator.domain.model.Login;
import com.api.aggregator.domain.model.Token;
import com.api.aggregator.domain.security.IAuthenticationFilter;
import com.api.aggregator.domain.validate.IValidateLogin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private IAuthenticationFilter auth;

    @Mock
    private IValidateLogin validateLogin;

    @Test
    void loginShouldReturnTokenWhenCredentialsAreValid() {
        Login login = new Login();
        login.setUsername("user");
        login.setPassword("password");
        String mockToken = "Token";

        doNothing().when(validateLogin).validateLogin(login);
        when(auth.authenticateLogin(login)).thenReturn(mockToken);

        ResponseEntity<?> response = authenticationController.login(login);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Token token = (Token) response.getBody();
        assertNotNull(token);
        assertEquals(mockToken, token.getTokenAuth());
        assertEquals("Bearer", token.getType());
        verify(auth).authenticateLogin(login);
    }

    @Test
    void loginShouldThrowExceptionWhenAuthenticationFails() {
        Login login = new Login();
        login.setUsername("user");
        login.setPassword("password");

        doNothing().when(validateLogin).validateLogin(login);
        when(auth.authenticateLogin(login)).thenThrow(new RuntimeException("Authentication failed"));

        Exception exception = assertThrows(RuntimeException.class, () -> authenticationController.login(login));
        assertEquals("Authentication failed", exception.getMessage());
        verify(auth).authenticateLogin(login);
    }
}