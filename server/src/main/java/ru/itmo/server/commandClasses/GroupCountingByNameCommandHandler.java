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
 * Класс команды group_counting_by_name
 */
public class GroupCountingByNameCommandHandler implements CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(GroupCountingByNameCommandHandler.class);
    /**
     * Метод execute команды group_counting_by_name
     *
     * @return Message[]
     */
    @Override
    public Message executeManual(int userId, Command command) throws SQLException {
        if (RouteManager.getInstance().getRoutesCount() != 0) {
            logger.info("Elements was group");
            return new Message(command.getUserUUID(),true, RouteManager.getInstance().groupRoutesByName().toString());

        } else {
            logger.info("Cannot group collection items");
            return new Message(command.getUserUUID(),true, "Cannot group collection items");
        }
    }

    @Override
    public boolean isAux() {
        return false;
    }

    @Override
    public boolean support(String commandName) {
        return "group_counting_by_name".equals(commandName);
    }

    @Override
    public Message executeScript(int userId, Command command, Object... args) throws IOException, SQLException {
        return executeManual(userId, command);
    }
}
