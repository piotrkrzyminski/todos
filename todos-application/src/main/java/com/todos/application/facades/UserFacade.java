package com.todos.application.facades;

import com.todos.application.facades.impl.exceptions.DuplicateUserException;
import com.todos.data.LoginData;
import com.todos.data.RegisterData;
import com.todos.model.User;

/**
 * Facade used for operation for user.
 */
public interface UserFacade {

    /**
     * Get data from registration page and validate it.
     * Perform register if user with specified data not exists in datasource.
     * Before register encrypt password for safety.
     *
     * @param data registration data.
     */
    void register(RegisterData data) throws Exception;

    /**
     * Checks in database if data passed form registration form is already exists.
     * User login and email must be unique.
     *
     * @param data registration data.
     */
    boolean isUserExists(RegisterData data) throws DuplicateUserException;
}
