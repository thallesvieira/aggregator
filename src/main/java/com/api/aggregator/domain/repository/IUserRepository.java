package com.api.aggregator.domain.repository;


import com.api.aggregator.domain.model.user.User;

public interface IUserRepository {
    User findByUsername(String username);
    User findById(Long userId);
}
