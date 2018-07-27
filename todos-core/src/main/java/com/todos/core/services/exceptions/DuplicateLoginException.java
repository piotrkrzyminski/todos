package com.todos.core.services.exceptions;

public class DuplicateLoginException extends DuplicateUserException {

    public DuplicateLoginException(String message) {
        super(message);
    }

    public DuplicateLoginException() {
        super();
    }
}
