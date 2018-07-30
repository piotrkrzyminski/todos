package com.todos.core.converters.impl;

import com.todos.core.converters.Converter;
import com.todos.data.UserData;
import com.todos.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

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
        userData.setFirstName(FIRST_NAME);
        userData.setSecondName(SECOND_NAME);
        userData.setSurname(SURNAME);
        userData.setContactNumber(PHONE);

        user.setLogin(LOGIN);
        user.setEmail(EMAIL);
        user.setFirstName(FIRST_NAME);
        user.setSecondName(SECOND_NAME);
        user.setSurname(SURNAME);
        user.setPassword(PASSWORD);
        user.setContactNumber(PHONE);
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
        assertEquals(FIRST_NAME, convertedData.getFirstName());
        assertEquals(SECOND_NAME, convertedData.getSecondName());
        assertEquals(SURNAME, convertedData.getSurname());
        assertEquals(PHONE, convertedData.getContactNumber());
    }

    @Test
    public void testConvertFromDataToModel() {
        User userModel = userConverter.convert(userData);

        assertEquals(LOGIN, userModel.getLogin());
        assertEquals(EMAIL, userModel.getEmail());
        assertEquals(FIRST_NAME, userModel.getFirstName());
        assertEquals(SECOND_NAME, userModel.getSecondName());
        assertEquals(SURNAME, userModel.getSurname());
        assertEquals(PHONE, userModel.getContactNumber());
    }
}