package com.todos.application.facades.impl;

import com.todos.api.services.UserService;
import com.todos.application.facades.impl.exceptions.DuplicateEmailException;
import com.todos.application.facades.impl.exceptions.DuplicateLoginException;
import com.todos.application.facades.impl.exceptions.DuplicateUserException;
import com.todos.core.services.DefaultUserService;
import com.todos.data.RegisterData;
import com.todos.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@EntityScan(basePackages = {"com.todos.model"})
@ComponentScan(basePackages = {"com.todos"})
public class DefaultUserFacadeTest {

    private DefaultUserFacade userFacade;

    @Mock
    private UserService userService;

    @Mock
    private RegisterData registerData;

    @Mock
    private User user;

    @Before
    public void setup() {
        userFacade = new DefaultUserFacade();
        registerData = mock(RegisterData.class);
        user = mock(User.class);

        userService = mock(DefaultUserService.class);

        userFacade.setUserService(userService);
    }

    /**
     * Test registration when data is passed properly and user not exists in database.
     * This operation should be finished successfully.
     */
    @Test
    public void testRegistrationSuccess() throws DuplicateUserException {
        when(registerData.getLogin()).thenReturn("dummy");
        when(registerData.getEmail()).thenReturn("dummy@email.com");
        when(registerData.getPassword()).thenReturn("qwerty");

        // user not exists in database
        when(userService.getUserByLogin("dummy")).thenReturn(null);
        when(userService.getUserByEmail("dummy@email.com")).thenReturn(null);

        userFacade.register(registerData);

        //convert register data to user
        when(user.getLogin()).thenReturn("dummy");
        when(user.getEmail()).thenReturn("dummy@email.com");
        when(user.getPassword()).thenReturn("qwerty");

        verify(userService).save(any()); // save method was called
    }

    /**
     * Test registration when user not exists in database but login is null.
     * This operation should failed because user login is required during registration.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegistrationLoginIsNull() throws DuplicateUserException {
        when(registerData.getLogin()).thenReturn(null);
        when(registerData.getEmail()).thenReturn("dummy@email.com");
        when(registerData.getPassword()).thenReturn("qwerty");

        userFacade.register(registerData);
    }

    /**
     * Test registration when user not exists in database but login field is empty.
     * This operation should failed because user login is required during registration.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegistrationEmptyLogin() throws DuplicateUserException {
        when(registerData.getLogin()).thenReturn("");
        when(registerData.getEmail()).thenReturn("dummy@email.com");
        when(registerData.getPassword()).thenReturn("qwerty");

        userFacade.register(registerData);
    }

    /**
     * Test registration when user not exists in database but email is null.
     * This operation should failed because email is required during registration.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegistrationEmailIsNull() throws DuplicateUserException {
        when(registerData.getLogin()).thenReturn("dummy");
        when(registerData.getEmail()).thenReturn(null);
        when(registerData.getPassword()).thenReturn("qwerty");

        userFacade.register(registerData);
    }

    /**
     * Test registration when user not exists in database but email field is empty.
     * This operation should failed because email is required during registration.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegistrationEmptyEmail() throws DuplicateUserException {
        when(registerData.getLogin()).thenReturn("dummy");
        when(registerData.getEmail()).thenReturn("");
        when(registerData.getPassword()).thenReturn("qwerty");

        userFacade.register(registerData);
    }

    /**
     * Test registration when user not exists in database but password is null.
     * This operation should failed because password is required during registration.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegistrationPasswordIsNull() throws DuplicateUserException {
        when(registerData.getLogin()).thenReturn("dummy");
        when(registerData.getEmail()).thenReturn("dummy@email.com");
        when(registerData.getPassword()).thenReturn(null);

        userFacade.register(registerData);
    }

    /**
     * Test registration when user not exists in database but password field is empty.
     * This operation should failed because password is required during registration.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegistrationEmptyPassword() throws DuplicateUserException {
        when(registerData.getLogin()).thenReturn("dummy");
        when(registerData.getEmail()).thenReturn("dummy@email.com");
        when(registerData.getPassword()).thenReturn("");

        userFacade.register(registerData);
    }

    /**
     * Test registration when user's login already exists in database.
     * This operation should failed because login must be unique.
     */
    @Test(expected = DuplicateLoginException.class)
    public void testRegistrationDuplicateLoginFailed() throws DuplicateUserException {
        when(registerData.getLogin()).thenReturn("dummy");
        when(registerData.getEmail()).thenReturn("dummy@email.com");
        when(registerData.getPassword()).thenReturn("qwerty");

        // user with login 'dummy' already exists in database
        when(userService.getUserByLogin("dummy")).thenReturn(new User());
        when(userService.getUserByEmail("dummy@email.com")).thenReturn(null);

        userFacade.register(registerData);
    }

    /**
     * Test registration when user's email already exists in database.
     * This operation should failed because email must be unique.
     */
    @Test(expected = DuplicateEmailException.class)
    public void testRegistrationDuplicateEmailFailed() throws DuplicateUserException {
        when(registerData.getLogin()).thenReturn("dummy");
        when(registerData.getEmail()).thenReturn("dummy@email.com");
        when(registerData.getPassword()).thenReturn("qwerty");

        // user with email 'dummy@email.com' already exists in database
        when(userService.getUserByLogin("dummy")).thenReturn(null);
        when(userService.getUserByEmail("dummy@email.com")).thenReturn(new User());

        userFacade.register(registerData);
    }

    @SpringBootApplication
    static class TestConfiguration {
    }
}