package ru.spb.itmo.asashina.yaprofessionaltask2.exception;

public class EntityDoesNotExistException extends RuntimeException {

    public EntityDoesNotExistException() {
    }

    public EntityDoesNotExistException(String message) {
        super(message);
    }
}
