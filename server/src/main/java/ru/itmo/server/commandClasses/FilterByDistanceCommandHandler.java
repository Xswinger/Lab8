package ru.itmo.server.commandClasses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Message;
import ru.itmo.server.RouteManager;
import ru.itmo.server.interfaces.CommandHandler;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Класс команды filter_by_distance
 */
public class FilterByDistanceCommandHandler implements CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(FilterByDistanceCommandHandler.class);
    /**
     * Метод execute команды add
     *
     * @return Message[]
     */
    @Override
    public Message executeManual(int userId, Command command) throws SQLException {
        if (RouteManager.getInstance().findMatchByDistance(Integer.parseInt(command.getParameterOfCommand()))) {
            logger.info("Command completed: no elements found with this distance value");
            return new Message(command.getUserUUID(),true, "No collection elements found with this distance value");
        }
//        int size = (int) RouteManager.getInstance().getRoutes().stream().filter(route -> route.getDistance() ==
//                Integer.parseInt(command.getParameterOfCommand())).count();
        logger.info("Command completed");
        return new Message(command.getUserUUID(),true, RouteManager.getInstance()
                .filterRoutesByDistance(Integer.parseInt(command.getParameterOfCommand())));
    }

    @Override
    public Message executeScript(int userId, Command command, Object... args) throws IOException, SQLException {
        return executeManual(userId, command);
    }

    @Override
    public boolean isAux() {
        return false;
    }

    @Override
    public boolean support(String commandName) {
        return "filter_by_distance".equals(commandName);
    }
}
