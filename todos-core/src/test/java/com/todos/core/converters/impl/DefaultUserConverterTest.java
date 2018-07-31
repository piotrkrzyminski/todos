package com.todos.core.converters.impl;

import com.todos.core.converters.Converter;
import com.todos.data.UserData;
import com.todos.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Test for user model and user data object converter.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DefaultUserConverterTest {

    private final static String LOGIN = "dummy";
    private final static String PASSWORD = "passw";
    private final static String EMAIL = "dummy@email.com";
    private final static String FIRST_NAME = "John";
    private final static String SECOND_NAME = "Adam";
    private final static String SURNAME = "Smith";
    private final static String PHONE = "329464808";

    private Converter<User, UserData> userConverter;

    private UserData userData;
    private User user;

    @Before
    public void setup() {
        userConverter = new DefaultUserConverter();
        userData = new UserData();
        user = new User();

        userData.setLogin(LOGIN);
        userData.setEmail(EMAIL);

        user.setLogin(LOGIN);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setActive(true);
        user.setEnabled(true);
    }

    /**
     * Test converting User model to User data object.
     */
    @Test
    public void testConvertFromModelToData() {
        UserData convertedData = userConverter.convert(user);

        assertEquals(LOGIN, convertedData.getLogin());
        assertEquals(EMAIL, convertedData.getEmail());
    }

    @Test
    public void testConvertFromDataToModel() {
        User userModel = userConverter.convert(userData);

        assertEquals(LOGIN, userModel.getLogin());
        assertEquals(EMAIL, userModel.getEmail());
    }
}