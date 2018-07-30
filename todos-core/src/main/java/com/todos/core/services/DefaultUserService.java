package com.todos.core.services;

import com.todos.api.repository.UserRepository;
import com.todos.api.services.UserService;
import com.todos.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class DefaultUserService implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultUserService.class);

    private UserRepository userRepository;

    @Override
    public void save(User user) {
        LOG.debug("Saving user with login " + user.getLogin() + " to database");
        userRepository.save(user);
        LOG.debug("User with login " + user.getLogin() + " saved successfully");
    }

    @Override
    public User getUserByLogin(String login) {
        LOG.debug("Searching for user with login " + login);
        return userRepository.findUserByLogin(login);
    }

    @Override
    public User getUserByEmail(String email) {
        LOG.debug("Searching for user with email " + email);
        return userRepository.findUserByEmail(email);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
