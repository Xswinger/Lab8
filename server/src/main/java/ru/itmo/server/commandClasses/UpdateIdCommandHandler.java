package ru.itmo.server.commandClasses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.common.dto.*;
import ru.itmo.server.RouteManager;
import ru.itmo.server.database.dao.RoutesDao;
import ru.itmo.server.database.dao.UsersDao;
import ru.itmo.server.interfaces.CommandHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

/**
 * Класс команды update_id
 */
public class UpdateIdCommandHandler implements CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(UpdateIdCommandHandler.class);

    /**
     * Метод execute команды update_id
     *
     * @return Message[]
     */
    @Override
    public Message executeManual(int userId, Command command) throws SQLException {
        int id = Integer.parseInt(command.getParameterOfCommand());
        Route updatedRoute = command.getRouteOfCommand();
        return getMessages(id, userId, updatedRoute, command);
    }

    /**
     * Метод execute команды update
     *
     * @return Message[]
     */
    @Override
    public Message executeScript(int userId, Command command, Object... args) throws IOException, SQLException {
        int id = Integer.parseInt(command.getParameterOfCommand());
        BufferedReader bufferedReader = (BufferedReader) args[0];
        Route updatedRoute = CreatingElement.scriptCreatingElement(bufferedReader);
        return getMessages(id, userId, updatedRoute, command);
    }

    private Message getMessages(long id, int userId, Route updatedRoute, Command command) throws SQLException {
        String backMessage = "Element with id " + id + " not found";
        Set<Long> userRoute = RoutesDao.getInstance().FindRoutesOfUser(userId);
        if (userRoute.contains(id)){
            RouteManager.getInstance().updateRoute(id, userId, updatedRoute);
            RoutesDao.getInstance().updateRouteById(id, userId, updatedRoute);
            logger.info("Command completed: route updated");
            return new Message(command.getUserUUID(),true, "Element update successfully");
        }
        logger.info("Command completed: route not updated");
        return new Message(command.getUserUUID(),true, backMessage);
    }

    @Override
    public boolean isAux() {
        return false;
    }

    @Override
    public boolean support(String commandName) {
        return "update".equals(commandName);
    }
}
