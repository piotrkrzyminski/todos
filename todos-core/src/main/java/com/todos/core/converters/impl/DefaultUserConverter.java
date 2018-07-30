package com.todos.core.converters.impl;

import com.todos.core.converters.Converter;
import com.todos.data.UserData;
import com.todos.model.User;
import org.springframework.stereotype.Component;

@Component("userConverter")
public class DefaultUserConverter implements Converter<User, UserData> {

    @Override
    public UserData convert(User model) {
        UserData userData = new UserData();

        userData.setId(model.getId());
        userData.setLogin(model.getLogin());
        userData.setEmail(model.getEmail());
        userData.setPassword(model.getPassword());

        return userData;
    }
}
