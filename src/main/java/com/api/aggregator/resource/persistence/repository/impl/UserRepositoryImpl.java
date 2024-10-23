package com.api.aggregator.resource.persistence.repository.impl;


import com.api.aggregator.domain.exception.ExceptionResponse;
import com.api.aggregator.domain.model.user.User;
import com.api.aggregator.domain.repository.IUserRepository;
import com.api.aggregator.resource.persistence.repository.IUserJPARepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

/**
 * Repository to User entity
 */
@Repository
public class UserRepositoryImpl implements IUserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    private IUserJPARepository userRepository;

    private ModelMapper mapper = new ModelMapper();

    /**
     * Find user entity by username and convert to user model to return to domain
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        logger.info("Getting user entity by username: {} and convert to model", username);
        return userRepository.findByUsername(username)
                .map(entity-> mapper.map(entity, User.class))
                .orElseThrow(() -> new ExceptionResponse("User not found!", HttpStatus.NOT_FOUND));
    }

    /**
     * Find user entity by id and convert to user model to return to domain
     * @param userId
     * @return
     */
    @Override
    public User findById(Long userId) {
        logger.info("Getting user entity by id: {} and convert to model", userId);
        return userRepository.findById(userId)
                .map(entity-> mapper.map(entity, User.class))
                .orElseThrow(() -> new ExceptionResponse("User not found!", HttpStatus.NOT_FOUND));
    }
}
