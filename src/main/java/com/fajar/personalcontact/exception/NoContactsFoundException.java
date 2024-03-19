package com.fajar.personalcontact.exception;

public class NoContactsFoundException extends RuntimeException {

    public NoContactsFoundException() {
        super("No contacts found.");
    }

    public NoContactsFoundException(String message) {
        super(message);
    }

    public NoContactsFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
