package ru.itmo.server.commandClasses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Message;
import ru.itmo.server.interfaces.CommandHandler;
import ru.itmo.server.Commands;

import java.io.IOException;
import java.util.Arrays;


/**
 * Класс команды help
 */
public class HelpCommandHandler implements CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(HelpCommandHandler.class);
    /**
     * Метод execute команды help
     *
     * @return Message[]
     */
    @Override
    public Message executeManual(int userId, Command command) throws IOException {
        StringBuilder arrayOfMessage = new StringBuilder();
        Arrays.stream(Commands.values()).forEachOrdered(commands ->
                arrayOfMessage.append(Commands.getFullNameCommand(commands)).append(" : ").append(Commands.getDescription(commands)).append("\n"));
        logger.info("Command completed");
        return new Message(command.getUserUUID(),true, arrayOfMessage.toString());
    }

    @Override
    public Message executeScript(int userId, Command command, Object... args) throws IOException {
        return executeManual(userId, command);
    }

    @Override
    public boolean isAux() {
        return false;
    }

    @Override
    public boolean support(String commandName) {
        return "help".equals(commandName);
    }
}
