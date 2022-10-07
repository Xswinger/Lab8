package ru.itmo.server.commandClasses.specialCommand;

import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Message;
import ru.itmo.server.interfaces.CommandHandler;

import java.io.IOException;
import java.sql.SQLException;

public class CheckConnectionHandler implements CommandHandler {
    @Override
    public boolean isAux() {
        return true;
    }

    @Override
    public boolean support(String commandName) {
        return "check".equals(commandName);
    }

    @Override
    public Message executeScript(int userId, Command command, Object... args) throws IOException, SQLException {
        return executeManual(userId, command);
    }

    @Override
    public Message executeManual(int userId, Command command) throws IOException, SQLException {
        return new Message(command.getUserUUID(), false, "Connection successfully");
    }
}
