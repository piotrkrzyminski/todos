package com.todos.core.services.exceptions;

public class DuplicateEmailException extends DuplicateUserException {

    public DuplicateEmailException(String message) {
        super(message);
    }

    public DuplicateEmailException() {
        super();
    }
}
