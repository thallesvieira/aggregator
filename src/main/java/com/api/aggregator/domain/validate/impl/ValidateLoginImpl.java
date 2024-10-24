package com.api.aggregator.domain.validate.impl;

import com.api.aggregator.domain.exception.ExceptionResponse;
import com.api.aggregator.domain.model.Login;
import com.api.aggregator.domain.validate.IValidateLogin;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Class to validate login
 */
@Component
public class ValidateLoginImpl implements IValidateLogin {

    @Override
    public void validateLogin(Login login) {
        if (login == null)
            throw new ExceptionResponse("Login is invalid", HttpStatus.BAD_REQUEST);

        if (login.getUsername() == null || login.getUsername().isBlank())
            throw new ExceptionResponse("Username is invalid", HttpStatus.BAD_REQUEST);

        if (login.getPassword() == null || login.getPassword().isBlank())
            throw new ExceptionResponse("Password is invalid", HttpStatus.BAD_REQUEST);
    }
}
