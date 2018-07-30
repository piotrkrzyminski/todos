package com.todos.application.facades.impl.exceptions;

/**
 * Exception thrown when user will try to register user that already exists in database.
 */
public class DuplicateUserException extends Exception {

    public DuplicateUserException(String message) {
        super(message);
    }
}
