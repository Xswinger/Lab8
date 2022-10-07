package ru.itmo.server.exceptions;

public class ScriptUnknownCommandException extends Exception {
    public ScriptUnknownCommandException(String message) {
        super(message);
    }
}
