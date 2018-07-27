package com.todos.application.facades.impl;

import com.todos.api.services.UserService;
import com.todos.application.facades.UserFacade;
import com.todos.core.converters.Converter;
import com.todos.core.services.exceptions.DuplicateEmailException;
import com.todos.core.services.exceptions.DuplicateLoginException;
import com.todos.core.services.exceptions.DuplicateUserException;
import com.todos.data.RegisterData;
import com.todos.data.UserData;
import com.todos.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component("userFacade")
public class DefaultUserFacade implements UserFacade {

    private BCryptPasswordEncoder encoder;
    private final UserService userService;
    private final Converter<User, UserData> userConverter;

    @Autowired
    public DefaultUserFacade(UserService userService, Converter<User, UserData> userConverter) {
        encoder = new BCryptPasswordEncoder();
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @Override
    public void register(RegisterData data) throws DuplicateUserException {
        // validate data before operation
        Assert.hasText(data.getLogin(), "Field [login] cannot be empty");
        Assert.hasText(data.getPassword(), "Field [password] cannot be empty");
        Assert.hasText(data.getEmail(), "Field [email] cannot be empty");

        //Check if user with login already exists
        if(userService.getUserByLogin(data.getLogin()) != null)
            throw new DuplicateLoginException("User with login " + data.getLogin() + " already exists");

        if(userService.getUserByEmail(data.getEmail()) != null)
            throw new DuplicateEmailException("User with email " + data.getEmail() + " already exists");

        // Create user with encoded password
        userService.save(convertToUser(data));
    }

    private User convertToUser(RegisterData data) {
        User user = new User();

        user.setLogin(data.getLogin());
        user.setEmail(data.getEmail());
        user.setPassword(encodePassword(data.getPassword()));

        return user;
    }

    private String encodePassword(String password) {
        return encoder.encode(password);
    }
}
