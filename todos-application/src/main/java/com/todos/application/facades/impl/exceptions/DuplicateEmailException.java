package com.todos.application.facades.impl.exceptions;

/**
 * Thrown during registration when user with selected email already exists in database.
 * User email must by unique.
 */
public class DuplicateEmailException extends DuplicateUserException {

    public DuplicateEmailException(String message) {
        super(message);
    }
}
