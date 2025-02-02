
package com.alessandro.book_finder_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class EmailAlreadyConfirmedException extends RuntimeException {
    public EmailAlreadyConfirmedException(String message) {
        super(message);
    }

    public EmailAlreadyConfirmedException(String message, Throwable cause) {
        super(message, cause);
    }
}
