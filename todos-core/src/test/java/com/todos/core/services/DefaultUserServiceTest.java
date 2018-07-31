package com.todos.core.services;

import com.todos.api.repository.UserRepository;
import com.todos.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@EntityScan(basePackages = {"com.todos.model"})
@EnableJpaRepositories(basePackages = "com.todos.api.repository")
public class DefaultUserServiceTest {

    private DefaultUserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @Before
    public void setup() {
        userService = new DefaultUserService();
        user = new User();

        userRepository = mock(UserRepository.class);

        userService.setUserRepository(userRepository);
    }

    /**
     * Test search for user by login.
     */
    @Test
    public void testFindUserByLoginFound() {
        when(userRepository.findUserByLogin(anyString())).thenReturn(user); // found user in database

        User result = userService.getUserByLogin("dummy");

        assertEquals(user, result);
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

    /**
     * Test search for user by email.
     */
    @Test
    public void testFindUserByEmailFound() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(user); // found user in database

        User result = userService.getUserByEmail("dummy@email.com");

        assertEquals(user, result);
    }

    /**
     * Test search for user by email (not found).
     */
    @Test
    public void testFindUserByEmailNotFound() {
        when(userRepository.findUserByLogin(anyString())).thenReturn(null); // user not found in database

        User result = userService.getUserByEmail("dummy@email.com");

        assertNull(result);
    }

    /**
     * Test save user to database.
     */
    @Test
    public void testSave() {
        user.setLogin("dummy");
        user.setEmail("email@test.com");
        user.setPassword("qwerty");

        userService.save(user);

        verify(userRepository).save(user);
    }


    @SpringBootApplication
    static class TestConfiguration {
    }
}