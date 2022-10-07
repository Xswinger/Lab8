package ru.itmo.server.commandClasses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Message;
import ru.itmo.server.RouteManager;
import ru.itmo.server.database.dao.RoutesDao;
import ru.itmo.server.interfaces.CommandHandler;
import ru.itmo.server.mainClasses.Invoker;

import java.sql.SQLException;
import java.util.Set;

/**
 * Класс команды clear
 */
public class ClearCommandHandler implements CommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(ClearCommandHandler.class);
    public ClearCommandHandler() {
        Invoker.getInstance().registerHandler(this);
    }
    /**
     * Метод execute команды clear
     * @return Message[]
     */
    @Override
    public Message executeManual(int userId, Command command) throws SQLException, InterruptedException {
        Set<Long> removedRoutes = RoutesDao.getInstance().FindRoutesOfUser(userId);
        for (long routeId : removedRoutes) {
            RoutesDao.getInstance().removeRoute(routeId, userId);
        }
        RouteManager.getInstance().removeRoutes(removedRoutes);
        logger.info("Routes owned by user with id {} have been removed", userId);
        return new Message(command.getUserUUID(),true, "Collection cleared");
    }
    @Override
    public Message executeScript(int userId, Command command, Object... args) throws SQLException, InterruptedException {
        return executeManual(userId, command);
    }

    @Override
    public boolean isAux() {
        return false;
    }

    @Override
    public boolean support(String commandName) {
        return "clear".equals(commandName);
    }
}
