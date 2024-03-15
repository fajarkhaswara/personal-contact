package com.fajar.personalcontact.exception;

public class EmptyContactListException extends RuntimeException {
    public EmptyContactListException(String message) {
        super(message);
    }
}
