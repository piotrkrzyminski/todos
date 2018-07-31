package com.todos.core.converters.impl;

import com.todos.core.converters.Converter;
import com.todos.data.RegisterData;
import com.todos.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DefaultRegisterConverterTest {

    private final static String LOGIN = "dummy";
    private final static String PASSWORD = "passw";
    private final static String EMAIL = "dummy@email.com";
    private final static String FIRST_NAME = "John";
    private final static String SECOND_NAME = "Adam";
    private final static String SURNAME = "Smith";
    private final static String PHONE = "329464808";

    private Converter<User, RegisterData> registerConverter;

    private RegisterData registerData;

    @Before
    public void setup() {
        registerConverter = new DefaultRegisterConverter();
        registerData = new RegisterData();

        registerData.setUsername(LOGIN);
        registerData.setPassword(PASSWORD);
        registerData.setEmail(EMAIL);
    }

    /**
     * Test conversion from register data to user.
     */
    @Test
    public void testConverterFromRegisterDataToUser() {
        User user = registerConverter.convert(registerData);

        assertEquals(LOGIN, user.getUsername());
        assertEquals(EMAIL, user.getEmail());
        assertNotNull(user.getPassword());
        assertTrue(user.isEnabled());
        assertFalse(user.isActive());
        assertNotNull(user.getCreationDate());
    }

    @SpringBootApplication
    static class TestConfiguration {
    }
}