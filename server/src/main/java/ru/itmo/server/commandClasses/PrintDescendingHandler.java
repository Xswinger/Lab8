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
 * Класс команды print_descending
 */
public class PrintDescendingHandler implements CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(PrintDescendingHandler.class);

    /**
     * Метод execute команды print_descending
     *
     * @return Message[]
     */
    @Override
    public Message executeManual(int userId, Command command) throws SQLException {
        if (RouteManager.getInstance().getRoutesCount() == 0) {
            return new Message(command.getUserUUID(),true, "Cannot sort: collection is empty");
        }
        List<Route> sortedList = RouteManager.getInstance().getSortedRoutesBySize();
        Collections.reverse(sortedList);
        List<Route> arrayOfMessage = new ArrayList<>(sortedList);
        logger.info("Command completed");
        return new Message(command.getUserUUID(),true, arrayOfMessage);
    }

    @Override
    public boolean isAux() {
        return false;
    }

    @Override
    public boolean support(String commandName) {
        return "print_descending".equals(commandName);
    }

    @Override
    public Message executeScript(int userId, Command command, Object... args) throws IOException, SQLException {
        return executeManual(userId, command);
    }
}
