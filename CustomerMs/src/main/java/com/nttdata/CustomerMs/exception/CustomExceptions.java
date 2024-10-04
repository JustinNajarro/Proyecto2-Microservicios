package com.nttdata.CustomerMs.exception;

public class CustomExceptions {

    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    public static class BadRequestException extends RuntimeException {
        public BadRequestException(String message) {
            super(message);
        }
    }

    public static class DniAlreadyExistsException extends RuntimeException {
        public DniAlreadyExistsException(String message) {
            super(message);
        }
    }

}

