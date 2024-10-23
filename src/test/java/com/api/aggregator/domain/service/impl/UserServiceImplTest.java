package com.api.aggregator.domain.service.impl;

import com.api.aggregator.domain.model.user.RoleType;
import com.api.aggregator.domain.model.user.User;
import com.api.aggregator.domain.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private IUserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setRole(RoleType.USER);
    }

    @Test
    void getByUsername() {
        String username = "username";
        when(userRepository.findByUsername(username)).thenReturn(user);

        User result = userService.getByUsername(username);

        assertNotNull(result);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void getById() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(user);

        User result = userService.getById(userId);

        assertNotNull(result);
        verify(userRepository, times(1)).findById(userId);
    }
}