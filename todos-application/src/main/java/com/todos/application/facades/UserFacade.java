package com.todos.application.facades;

import com.todos.application.facades.impl.exceptions.DuplicateUserException;
import com.todos.data.RegisterData;

/**
 * Facade used for operation for user.
 */
public interface UserFacade {

    /**
     * Get data from registration page and validate it.
     * Perform register if user with specified data not exists in datasource.
     *
     * @param data registration form data.
     */
    void register(RegisterData data) throws DuplicateUserException;
}
