package ru.itmo.server.commandClasses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.common.dto.*;
import ru.itmo.server.RouteManager;
import ru.itmo.server.database.dao.RoutesDao;
import ru.itmo.server.interfaces.CommandHandler;
import ru.itmo.server.utils.AssignmentOfAutomaticallyGeneratedFields;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Класс команды add_if_max
 */
public class AddIfMaxCommandHandler implements CommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(AddIfMaxCommandHandler.class);

    /**
     * Метод execute команды add_if_max
     * @return Message[]
     */
    @Override
    public Message executeManual(int userId, Command command) throws SQLException, InterruptedException {
        if (RouteManager.getInstance().getSortedRoutesByNameLessThen(command.getRouteOfCommand().getName()).size()
                == RouteManager.getInstance().getRoutesCount()) {
            long routeId = RoutesDao.getInstance().generateId();
            Route addedRoute = AssignmentOfAutomaticallyGeneratedFields.generate(command.getRouteOfCommand(), routeId);
            updateDatabase(userId, routeId, addedRoute);
            RouteManager.getInstance().addRoute(addedRoute);
            logger.info("Element added successfully");
            return new Message(command.getUserUUID(),true, "Element added successfully");
        } else {
            logger.warn("The element was not added to the collection");
            return new Message(command.getUserUUID(),true, "The element was not added to the collection");
        }
    }

    @Override
    public Message executeScript(int userId, Command command, Object... args) throws IOException, SQLException, InterruptedException {
        BufferedReader bufferedReader = (BufferedReader) args[0];
        long routeId = RoutesDao.getInstance().generateId();
        Route addedRoute = AssignmentOfAutomaticallyGeneratedFields.generate(
                CreatingElement.scriptCreatingElement(bufferedReader), routeId);
        if (RouteManager.getInstance().getSortedRoutesByNameLessThen(command.getRouteOfCommand().getName()).size()
                == RouteManager.getInstance().getRoutesCount()) {
            updateDatabase(userId, routeId, addedRoute);
            RouteManager.getInstance().addRoute(addedRoute);
            logger.info("Element added successfully");
            return new Message(command.getUserUUID(),true, "Element added successfully");
        } else {
            logger.warn("The element was not added to the collection");
            return new Message(command.getUserUUID(),true, "The element was not added to the collection");
        }
    }

    private void updateDatabase(int userId, long routeId, Route addedRoute) throws SQLException {
        RoutesDao.getInstance().addRoute(userId, routeId, addedRoute);
    }

    @Override
    public boolean isAux() {
        return false;
    }

    @Override
    public boolean support(String commandName) {
        return "add_if_max".equals(commandName);
    }
}
