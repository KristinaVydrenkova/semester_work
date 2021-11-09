package ru.itis.exceptions;

public class PhotoException extends RuntimeException{
    public PhotoException() {
    }

    public PhotoException(String message) {
        super(message);
    }

    public PhotoException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhotoException(Throwable cause) {
        super(cause);
    }
}
