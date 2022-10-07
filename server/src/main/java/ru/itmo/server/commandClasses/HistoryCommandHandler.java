package ru.itmo.server.commandClasses;

import org.slf4j.LoggerFactory;
import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Message;
import ru.itmo.server.interfaces.CommandHandler;
import ru.itmo.server.mainClasses.HistoryManager;

import java.io.IOException;

/**
 * Класс команды history
 */
public class HistoryCommandHandler implements CommandHandler {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HistoryCommandHandler.class);

    /**
     * Метод execute команды collection
     *
     * @return Message[]
     */
    @Override
    public Message executeManual(int userId, Command command) {
        String history = HistoryManager.getInstance().historyOfUser(userId);
        logger.info("Command completed");
        return new Message(command.getUserUUID(),true, history);
    }

    @Override
    public boolean isAux() {
        return false;
    }

    @Override
    public boolean support(String commandName) {
        return "history".equals(commandName);
    }

    @Override
    public Message executeScript(int userId, Command command, Object... args) throws IOException {
        return executeManual(userId, command);
    }
}
