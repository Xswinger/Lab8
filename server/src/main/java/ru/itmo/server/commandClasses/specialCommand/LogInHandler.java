package ru.itmo.server.commandClasses.specialCommand;

import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Message;
import ru.itmo.server.RouteManager;
import ru.itmo.server.interfaces.CommandHandler;

import java.io.IOException;
import java.sql.SQLException;

public class LogInHandler implements CommandHandler {
    @Override
    public boolean isAux() {
        return true;
    }

    @Override
    public boolean support(String commandName) {
        return "registration".equals(commandName);
    }

    @Override
    public Message executeScript(int userId, Command command, Object... args) throws IOException, SQLException {
        return executeManual(userId, command);
    }

    @Override
    public Message executeManual(int userId, Command command) throws IOException, SQLException {
        if (userId == -1){
            return new Message(command.getUserUUID(), false, "Wrong login or password");
        } else {
            return new Message(command.getUserUUID(), true, "Login successful\nRoutes upload");
        }
    }
}
