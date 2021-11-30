package com.example.studytobyspring.chapter4;

public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException(Throwable cause) {
        super(cause);
    }
}
