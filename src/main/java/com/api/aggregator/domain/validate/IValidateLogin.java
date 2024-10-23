package com.api.aggregator.domain.validate;

import com.api.aggregator.domain.model.Login;

public interface IValidateLogin {
    void validateLogin(Login login);
}
