package ru.itmo.server.commandClasses;

import ru.itmo.common.dto.*;
import org.slf4j.LoggerFactory;
import ru.itmo.server.RouteManager;
import ru.itmo.server.database.dao.RoutesDao;
import ru.itmo.server.interfaces.CommandHandler;
import ru.itmo.server.utils.AssignmentOfAutomaticallyGeneratedFields;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Класс команды add
 */
public class AddElementCommandHandler implements CommandHandler {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AddElementCommandHandler.class);


    /**
     * Метод execute команды add(ручной ввод)
     *
     * @return Message[]
     */
    @Override
    public Message executeManual(int userId, Command command) throws InterruptedException {
        try {
            long routeId = RoutesDao.getInstance().generateId();
            Route addedRoute = AssignmentOfAutomaticallyGeneratedFields.generate(command.getRouteOfCommand(), routeId);
            updateDatabase(userId, routeId, addedRoute);
            RouteManager.getInstance().addRoute(addedRoute);
            logger.info("Element added successfully");
            return new Message(command.getUserUUID(), true, "Element added successfully");
        } catch (NumberFormatException | SQLException ex) {
            logger.warn("Invalid input variable format");
            return new Message(command.getUserUUID(), true, ex.getMessage() + " - Invalid input variable format");
        }
    }

    /**
     * Метод execute команды add(исполнение скрипта)
     */
    @Override
    public Message executeScript(int userId, Command command, Object... args) throws IOException, InterruptedException {
        try {
            long routeId = RoutesDao.getInstance().generateId();
            BufferedReader bufferedReader = (BufferedReader) args[0];
            Route addedRoute = AssignmentOfAutomaticallyGeneratedFields.generate(
                    CreatingElement.scriptCreatingElement(bufferedReader), routeId);
            updateDatabase(userId, routeId, addedRoute);
            RouteManager.getInstance().addRoute(addedRoute);
            return new Message(command.getUserUUID(), true, "Element added successfully");
        } catch (NumberFormatException | SQLException ex) {
            return new Message(command.getUserUUID(),true, "Error during element creation:\n" + ex.getMessage()
                    + " - Invalid input variable format");
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
        return "add".equals(commandName);
    }
}
