package com.api.aggregator.domain.service.impl;

import com.api.aggregator.domain.model.user.User;
import com.api.aggregator.domain.repository.IUserRepository;
import com.api.aggregator.domain.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service responsible to actions about user
 */
@Service
public class UserServiceImpl implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserRepository userRepository;

    /**
     * Get user by username
     * @param username
     * @return
     */
    @Override
    public User getByUsername(String username) {
        logger.info("Getting user by username: {}", username);
        return userRepository.findByUsername(username);
    }

    /**
     * Get user by id
     *
     * @param userId
     * @return
     */
    @Override
    public User getById(Long userId) {
        logger.info("Getting user by id: {}", userId);
        return userRepository.findById(userId);
    }
}
