package ru.itmo.server.commandClasses.specialCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Message;
import ru.itmo.server.interfaces.CommandHandler;
import ru.itmo.server.mainClasses.HistoryManager;

import java.io.IOException;
import java.sql.SQLException;

public class ClientExitHandler implements CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(ClientExitHandler.class);

    @Override
    public boolean isAux() {
        return true;
    }

    @Override
    public boolean support(String commandName) {
        return "exit".equals(commandName);
    }

    @Override
    public Message executeScript(int userId, Command command, Object... args) throws IOException, SQLException {
        return executeManual(userId, command);
    }

    @Override
    public Message executeManual(int userId, Command command) throws IOException, SQLException {
        logger.info("End of work with the client");
        HistoryManager.getInstance().clearHistory(userId);
        return new Message(command.getUserUUID(), false, "Exiting...");
    }
}
