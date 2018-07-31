package com.todos.core.converters.impl;

import com.todos.core.converters.Converter;
import com.todos.data.RegisterData;
import com.todos.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Converter user to convert {@link com.todos.data.RegisterData} to {@link com.todos.model.User}
 *
 * @author Piotr Krzyminski
 */
@Component
public class DefaultRegisterConverter implements Converter<User, RegisterData> {

    private PasswordEncoder passwordEncoder;

    /**
     * Avoid converting from user to register data
     */
    @Override
    public RegisterData convert(User model) {
        return null;
    }

    @Override
    public User convert(RegisterData data) {
        User model = new User();

        if (data.getUsername() != null) {
            model.setUsername(data.getUsername());
        }

        if (data.getEmail() != null) {
            model.setEmail(data.getEmail());
        }

        if (data.getPassword() != null) {
            model.setPassword(passwordEncoder.encode(data.getPassword())); // perform password encoding for safety
        }

        model.setEnabled(true); // set account enabled before first login
        model.setActive(false); // user is not active until login
        model.setCreationDate(new Date()); // set creation date to current time

        return model;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
