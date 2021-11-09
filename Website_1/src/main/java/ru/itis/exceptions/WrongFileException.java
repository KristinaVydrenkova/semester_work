package ru.itis.exceptions;

public class WrongFileException extends RuntimeException{
    public WrongFileException() {
    }

    public WrongFileException(String message) {
        super(message);
    }

    public WrongFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongFileException(Throwable cause) {
        super(cause);
    }

}
