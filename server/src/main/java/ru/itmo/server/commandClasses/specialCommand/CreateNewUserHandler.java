package ru.itmo.server.commandClasses.specialCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Message;
import ru.itmo.server.RouteManager;
import ru.itmo.server.Server;
import ru.itmo.server.database.dao.UsersDao;
import ru.itmo.server.interfaces.CommandHandler;

import java.io.IOException;
import java.sql.SQLException;

public class CreateNewUserHandler implements CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    @Override
    public boolean isAux() {
        return true;
    }

    @Override
    public boolean support(String commandName) {
        return "creating new user".equals(commandName);
    }

    @Override
    public Message executeScript(int userId, Command command, Object... args) throws IOException, SQLException {
        return executeManual(userId, command);
    }

    @Override
    public Message executeManual(int userId, Command command) throws IOException, SQLException {
        logger.info("Check user registration");
        String string;
        Message message;
        if (UsersDao.getInstance().addUser(command.getLogin(), command.getPassword()) == -1) {
            string = "Cannot register new user";
            message = new Message(command.getUserUUID(), false, string);
        }
        else {
            string = "User registered successfully";
            RouteManager.getInstance();
            message = new Message(command.getUserUUID(), true, string);
        }
        return message;
    }
}
