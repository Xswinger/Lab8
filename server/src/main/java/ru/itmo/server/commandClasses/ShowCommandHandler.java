package ru.itmo.server.commandClasses;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Message;
import ru.itmo.common.dto.Route;
import ru.itmo.server.RouteManager;
import ru.itmo.server.interfaces.CommandHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Класс команды show
 */
public class ShowCommandHandler implements CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(ShowCommandHandler.class);

    /**
     * Метод execute команды show
     *
     * @return Message[]
     */
    @Override
    public Message executeManual(int userId, Command command) throws SQLException {
        Message message;
        if (RouteManager.getInstance().getRoutesCount() != 0) {
            List<Route> arrayOfCommand = RouteManager.getInstance().getSortedRoutesBySize();
            logger.info("Command completed");
            message = new Message(command.getUserUUID(),true, "Collection elements:", arrayOfCommand);
        } else {
            logger.info("Command completed: collection has no elements");
            message = new Message(command.getUserUUID(),true, "The collection has no elements");
        }
        return message;

    }

    @Override
    public boolean isAux() {
        return false;
    }

    @Override
    public boolean support(String commandName) {
        return "show".equals(commandName);
    }

    @Override
    public Message executeScript(int userId, Command command, Object... args) throws IOException, SQLException {
        return executeManual(userId, command);
    }
}
