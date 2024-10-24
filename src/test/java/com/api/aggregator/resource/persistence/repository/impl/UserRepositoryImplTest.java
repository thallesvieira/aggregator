package com.api.aggregator.resource.persistence.repository.impl;

import com.api.aggregator.domain.exception.ExceptionResponse;
import com.api.aggregator.domain.model.user.User;
import com.api.aggregator.resource.persistence.entity.UserEntity;
import com.api.aggregator.resource.persistence.repository.IUserJPARepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private IUserJPARepository userRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private UserRepositoryImpl userRepositoryImpl;


    @Test
    void findByUsernameSuccess() {
        String username = "username";

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);

        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(mapper.map(userEntity, User.class)).thenReturn(user);

        User result = userRepositoryImpl.findByUsername(username);

        assertNotNull(result);
        assertEquals(result, user);
    }

    @Test
    void findByUsernameNotFound() {
        String username = "nonexistentuser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        ExceptionResponse exception = assertThrows(ExceptionResponse.class, () -> userRepositoryImpl.findByUsername(username));
        assertEquals("User not found!", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void findByIdSuccess() {
        Long userId = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(mapper.map(userEntity, User.class)).thenReturn(user);

        User result = userRepositoryImpl.findById(userId);

        assertNotNull(result);
        assertEquals(result, user);
    }

    @Test
    void findByIdNotFound() {
        Long userId = 999L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ExceptionResponse exception = assertThrows(ExceptionResponse.class, () -> userRepositoryImpl.findById(userId));
        assertEquals("User not found!", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}