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

        if (model.getId() != null) {
            userData.setId(model.getId());
        }

        if (model.getUsername() != null) {
            userData.setLogin(model.getUsername());
        }

        if (model.getEmail() != null) {
            userData.setEmail(model.getEmail());
        }

        return userData;
    }

    @Override
    public User convert(UserData data) {
        User user = new User();

        if (data.getLogin() != null) {
            user.setUsername(data.getLogin());
        }

        if (data.getEmail() != null) {
            user.setEmail(data.getEmail());
        }

        return user;
    }
}
