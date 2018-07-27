package com.todos.core.services;

import com.todos.api.repository.UserRepository;
import com.todos.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@EntityScan(basePackages = {"com.todos.model"})
@EnableJpaRepositories(basePackages = "com.todos.api.repository")
@Transactional
public class DefaultUserServiceTest {

    private DefaultUserService userService;

    private UserRepository userRepository;

    @Before
    public void setup() {
        userService = new DefaultUserService();

        userRepository = mock(UserRepository.class);

        userService.setUserRepository(userRepository);
    }

    /**
     * Test search for user by login.
     */
    @Test
    public void testFindUserByLoginFound() {
        User dummyUser = new User();

        when(userRepository.findUserByLogin(anyString())).thenReturn(dummyUser); // found user in database

        User result = userService.getUserByLogin("dummy");

        assertEquals(dummyUser, result);
    }

    /**
     * Test search for user by login (not found).
     */
    @Test
    public void testFindUserByLoginNotFound() {
        when(userRepository.findUserByLogin(anyString())).thenReturn(null); // user not found in database

        User result = userService.getUserByLogin("dummy");

        assertNull(result);
    }


    @SpringBootApplication
    static class TestConfiguration {
    }
}