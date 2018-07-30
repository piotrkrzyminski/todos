package com.todos.application.facades.impl.exceptions;

/**
 * Thrown during registration when user with selected login already exists in database.
 * User login must by unique.
 */
public class DuplicateLoginException extends DuplicateUserException {

    public DuplicateLoginException(String message) {
        super(message);
    }
}
