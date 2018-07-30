package com.todos.application.facades.impl;

import com.todos.api.services.UserService;
import com.todos.application.facades.UserFacade;
import com.todos.application.facades.impl.exceptions.DuplicateEmailException;
import com.todos.core.converters.Converter;
import com.todos.application.facades.impl.exceptions.DuplicateLoginException;
import com.todos.application.facades.impl.exceptions.DuplicateUserException;
import com.todos.data.RegisterData;
import com.todos.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * This facade is responsible for all operations on users.
 */
@Component("userFacade")
public class DefaultUserFacade implements UserFacade {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultUserFacade.class);

    private UserService userService;
    private Converter<User, RegisterData> registerConverter;

    @Override
    public void register(RegisterData data) throws Exception {
        LOG.info("Performing registration. Login [" + data.getLogin() + "], email [" + data.getEmail() + "]");

        //Check if user with login already exists and register him
        if(!isUserExists(data)) {
            User user = registerConverter.convert(data);

            userService.save(user);
            LOG.info("Registration finished successfully");
        }
    }

    @Override
    public boolean isUserExists(RegisterData data) throws DuplicateUserException {

        validateRegisterData(data); // validate registration data

        if(userService.getUserByLogin(data.getLogin()) != null)
            throw new DuplicateLoginException("User with login " + data.getLogin() + " already exists");

        if(userService.getUserByEmail(data.getEmail()) != null)
            throw new DuplicateEmailException("User with email " + data.getEmail() + " already exists");

        return false;
    }

    private void validateRegisterData(RegisterData data) {
        Assert.hasText(data.getLogin(), "Field [login] cannot be empty");
        Assert.hasText(data.getPassword(), "Field [password] cannot be empty");
        Assert.hasText(data.getEmail(), "Field [email] cannot be empty");
        Assert.hasText(data.getFirstName(),"Field [first name] cannot be empty");
        Assert.hasText(data.getSurname(),"Field [surname] cannot be empty");
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRegisterConverter(Converter<User, RegisterData> registerConverter) {
        this.registerConverter = registerConverter;
    }
}
