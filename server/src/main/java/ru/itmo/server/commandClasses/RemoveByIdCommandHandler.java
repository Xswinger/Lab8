package ru.itmo.server.commandClasses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Message;

import ru.itmo.server.RouteManager;
import ru.itmo.server.database.dao.RoutesDao;
import ru.itmo.server.interfaces.CommandHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс команды remove_by_id
 */
public class RemoveByIdCommandHandler implements CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(RemoveByIdCommandHandler.class);

    /**
     * Метод execute команды remove_by_id
     *
     * @return Message[]
     */
    @Override
    public Message executeManual(int userId, Command command) throws SQLException {
        Set<Long> userRoutes = RoutesDao.getInstance().FindRoutesOfUser(userId);
        if (userRoutes.contains(Long.parseLong(command.getParameterOfCommand()))){
            RoutesDao.getInstance().removeRoute(Long.parseLong(command.getParameterOfCommand()), userId);
            RouteManager.getInstance().removeRoutes(new HashSet<>(Collections.singleton(Long.parseLong
                    (command.getParameterOfCommand()))));
            logger.info("Command completed: element deleted successfully");
            return new Message(command.getUserUUID(),true, "Element deleted successfully");
        } else {
            logger.info("Command completed: user does not have permission to delete this ");
            return new Message(command.getUserUUID(),true, "Not have permission to " +
                    "delete this route");
        }
    }

    @Override
    public boolean isAux() {
        return false;
    }

    @Override
    public boolean support(String commandName) {
        return "remove_by_id".equals(commandName);
    }

    @Override
    public Message executeScript(int userId, Command command, Object... args) throws IOException, SQLException {
        return executeManual(userId, command);
    }
}
