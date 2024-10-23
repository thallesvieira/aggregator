package com.api.aggregator.domain.validate.impl;

import com.api.aggregator.domain.model.Login;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ValidateLoginImplTest {
    @InjectMocks
    private ValidateLoginImpl validateLogin;

    @Test
    void validate_Login_When_Ok() {
        Login login = new Login();
        login.setUsername("user");
        login.setPassword("password");

        validateLogin.validateLogin(login);
    }

    @Test
    void throw_Exception_When_Login_Is_Null() {
        Exception exception = assertThrows(RuntimeException.class, () -> validateLogin.validateLogin(null));
        assertEquals("Login is invalid", exception.getMessage());
    }

    @Test
    void throw_Exception_When_Username_Is_Null() {
        Login login = new Login();

        Exception exception = assertThrows(RuntimeException.class, () -> validateLogin.validateLogin(login));
        assertEquals("Username is invalid", exception.getMessage());
    }

    @Test
    void throw_Exception_When_Username_Is_Blank() {
        Login login = new Login();
        login.setUsername("");

        Exception exception = assertThrows(RuntimeException.class, () -> validateLogin.validateLogin(login));
        assertEquals("Username is invalid", exception.getMessage());
    }

    @Test
    void throw_Exception_When_Password_Is_Null() {
        Login login = new Login();
        login.setUsername("username");

        Exception exception = assertThrows(RuntimeException.class, () -> validateLogin.validateLogin(login));
        assertEquals("Password is invalid", exception.getMessage());
    }

    @Test
    void throw_Exception_When_Password_Is_Blank() {
        Login login = new Login();
        login.setUsername("user");
        login.setPassword("");
        Exception exception = assertThrows(RuntimeException.class, () -> validateLogin.validateLogin(login));
        assertEquals("Password is invalid", exception.getMessage());
    }
}