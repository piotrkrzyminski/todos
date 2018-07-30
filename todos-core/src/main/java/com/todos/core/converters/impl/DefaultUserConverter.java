package com.todos.core.converters.impl;

import com.todos.core.converters.Converter;
import com.todos.data.UserData;
import com.todos.model.User;
import org.springframework.stereotype.Component;

/**
 * Default converter for {@link User} and {@link UserData}
 *
 * @author Piotr Krzyminski
 */
@Component
public class DefaultUserConverter implements Converter<User, UserData> {

    @Override
    public UserData convert(User model) {
        UserData userData = new UserData();

        if(model.getId() != null) {
            userData.setId(model.getId());
        }

        if(model.getLogin() != null) {
            userData.setLogin(model.getLogin());
        }

        if(model.getEmail() != null) {
            userData.setEmail(model.getEmail());
        }

        if(model.getContactNumber() != null) {
            userData.setContactNumber(model.getContactNumber());
        }

        if(model.getFirstName() != null) {
            userData.setFirstName(model.getFirstName());
        }

        if(model.getSecondName() != null) {
            userData.setSecondName(model.getSecondName());
        }

        if(model.getSecondName() != null) {
            userData.setSurname(model.getSurname());
        }

        return userData;
    }

    @Override
    public User convert(UserData data) {
        User user = new User();

        if(data.getLogin() != null) {
            user.setLogin(data.getLogin());
        }

        if(data.getEmail() != null) {
            user.setEmail(data.getEmail());
        }

        if(data.getContactNumber() != null) {
            user.setContactNumber(data.getContactNumber());
        }

        if(data.getFirstName() != null) {
            user.setFirstName(data.getFirstName());
        }

        if(data.getSecondName() != null) {
            user.setSecondName(data.getSecondName());
        }

        if(data.getSurname() != null) {
            user.setSurname(data.getSurname());
        }

        return user;
    }
}
