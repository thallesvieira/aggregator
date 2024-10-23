package com.api.aggregator.domain.service;

import com.api.aggregator.domain.model.user.User;

public interface IUserService {
    User getByUsername(String username);
    User getById(Long userId);
}
