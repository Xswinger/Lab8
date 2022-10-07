package ru.itmo.server.interfaces;

public interface CommandHandler extends CommandManual, CommandScript {
    boolean isAux();
    boolean support(String commandName);
}
