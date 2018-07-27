package com.todos.core.services.exceptions;

/**
 * Exception thrown when user will try to register user that already exists in database.
 */
public class DuplicateUserException extends Throwable {

    public DuplicateUserException(String message) {
        super(message);
    }

    public DuplicateUserException() {
        super();
    }
}
