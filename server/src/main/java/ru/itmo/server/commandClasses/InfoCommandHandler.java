package ru.itmo.server.commandClasses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Message;
import ru.itmo.server.RouteManager;
import ru.itmo.server.database.dao.UsersDao;
import ru.itmo.server.interfaces.CommandHandler;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Класс команды info
 */
public class InfoCommandHandler implements CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(InfoCommandHandler.class);

    /**
     * Метод execute команды info
     *
     * @return Message[]
     */
    @Override
    public Message executeManual(int userId, Command command) throws SQLException {
        logger.info("Command completed");
        return new Message(command.getUserUUID(),true,
                "Collection information:"
                        + "\nCollection type: " + RouteManager.getInstance().getCollectionType()
                        + "\nRegistration date: " + UsersDao.getInstance().getRegistrationDate(userId)
                        + "\nAmount of elements: " + RouteManager.getInstance().getRoutesCount());
    }

    @Override
    public boolean isAux() {
        return false;
    }

    @Override
    public boolean support(String commandName) {
        return "info".equals(commandName);
    }

    @Override
    public Message executeScript(int userId, Command command, Object... args) throws IOException, SQLException {
        return executeManual(userId, command);
    }
}
