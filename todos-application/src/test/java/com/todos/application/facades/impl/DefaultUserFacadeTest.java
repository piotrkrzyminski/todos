package com.todos.application.facades.impl;

import com.todos.api.services.UserService;
import com.todos.application.facades.impl.exceptions.DuplicateEmailException;
import com.todos.application.facades.impl.exceptions.DuplicateLoginException;
import com.todos.core.converters.Converter;
import com.todos.core.converters.impl.DefaultRegisterConverter;
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

    private final static String LOGIN = "dummy";
    private final static String PASSWORD = "passw";
    private final static String EMAIL = "dummy@email.com";
    private final static String FIRST_NAME = "John";
    private final static String SURNAME = "Smith";

    private DefaultUserFacade userFacade;

    @Mock
    private UserService userService;

    @Mock
    private Converter<User, RegisterData> registerConverter;

    @Mock
    private RegisterData registerData;

    @Before
    public void setup() {
        userFacade = new DefaultUserFacade();

        userService = mock(DefaultUserService.class);
        registerConverter = mock(DefaultRegisterConverter.class);

        userFacade.setUserService(userService);
        userFacade.setRegisterConverter(registerConverter);

        registerData = mock(RegisterData.class);
        when(registerData.getUsername()).thenReturn(LOGIN);
        when(registerData.getEmail()).thenReturn(EMAIL);
        when(registerData.getPassword()).thenReturn(PASSWORD);
    }

    /**
     * Test registration when data is passed properly and user not exists in database.
     * This operation should be finished successfully.
     */
    @Test
    public void testRegistrationSuccess() throws Exception {
        // user not exists in database
        when(userService.getUserByLogin("dummy")).thenReturn(null);
        when(userService.getUserByEmail("dummy@email.com")).thenReturn(null);

        userFacade.register(registerData);

        verify(userService, times(1)).save(any()); // save method was called
    }

    /**
     * Test registration when user not exists in database but login is null.
     * This operation should failed because user login is required during registration.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegistrationLoginIsNull() throws Exception {
        when(registerData.getUsername()).thenReturn(null);

        userFacade.register(registerData);

        verify(userService, never()).save(any()); // registration is not performed.
    }

    /**
     * Test registration when user not exists in database but login field is empty.
     * This operation should failed because user login is required during registration.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegistrationEmptyLogin() throws Exception {
        when(registerData.getUsername()).thenReturn("");

        userFacade.register(registerData);

        verify(userService, never()).save(any()); // registration is not performed.
    }

    /**
     * Test registration when user not exists in database but email is null.
     * This operation should failed because email is required during registration.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegistrationEmailIsNull() throws Exception {
        when(registerData.getEmail()).thenReturn(null);

        userFacade.register(registerData);

        verify(userService, never()).save(any()); // registration is not performed.
    }

    /**
     * Test registration when user not exists in database but email field is empty.
     * This operation should failed because email is required during registration.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegistrationEmptyEmail() throws Exception {
        when(registerData.getEmail()).thenReturn("");

        userFacade.register(registerData);

        verify(userService, never()).save(any()); // registration is not performed.
    }

    /**
     * Test registration when user not exists in database but password is null.
     * This operation should failed because password is required during registration.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegistrationPasswordIsNull() throws Exception {
        when(registerData.getPassword()).thenReturn(null);

        userFacade.register(registerData);

        verify(userService, never()).save(any()); // registration is not performed.
    }

    /**
     * Test registration when user not exists in database but password field is empty.
     * This operation should failed because password is required during registration.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegistrationEmptyPassword() throws Exception {
        when(registerData.getPassword()).thenReturn("");

        userFacade.register(registerData);

        verify(userService, never()).save(any()); // registration is not performed.
    }

    /**
     * Test registration when user's login already exists in database.
     * This operation should failed because login must be unique.
     */
    @Test(expected = DuplicateLoginException.class)
    public void testRegistrationDuplicateLoginFailed() throws Exception {
        // user with login 'dummy' already exists in database
        when(userService.getUserByLogin("dummy")).thenReturn(new User());
        when(userService.getUserByEmail("dummy@email.com")).thenReturn(null);

        userFacade.register(registerData);

        verify(userService, never()).save(any()); // registration is not performed.
    }

    /**
     * Test registration when user's email already exists in database.
     * This operation should failed because email must be unique.
     */
    @Test(expected = DuplicateEmailException.class)
    public void testRegistrationDuplicateEmailFailed() throws Exception {
        // user with email 'dummy@email.com' already exists in database
        when(userService.getUserByLogin("dummy")).thenReturn(null);
        when(userService.getUserByEmail("dummy@email.com")).thenReturn(new User());

        userFacade.register(registerData);

        verify(userService, never()).save(any()); // registration is not performed.
    }

    @SpringBootApplication
    static class TestConfiguration {
    }
}