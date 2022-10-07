package ru.itmo.server.mainClasses;

import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Message;
import ru.itmo.server.commandClasses.specialCommand.CheckConnectionHandler;
import ru.itmo.server.commandClasses.specialCommand.ClientExitHandler;
import ru.itmo.server.commandClasses.specialCommand.CreateNewUserHandler;
import ru.itmo.server.commandClasses.specialCommand.LogInHandler;
import ru.itmo.server.interfaces.CommandHandler;
import ru.itmo.server.Commands;
import ru.itmo.server.commandClasses.*;
import ru.itmo.server.exceptions.ScriptUnknownCommandException;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс выборки команды
 */
public class Invoker {
    private static Invoker instance = null;
    public static Invoker getInstance() {
        if (instance == null) {
            instance = new Invoker();
        }
        return instance;
    }
    private final List<CommandHandler> commandHandlers = new ArrayList<>();

    public void registerHandler(CommandHandler commandHandler) {
        commandHandlers.add(commandHandler);
    }

    /**
     * Метод, вызывающий определенную команду (для ручного ввода)
     *
     * @throws IOException - если в команде execute_script будет исключение
     */
    public Message executeCommandManual(int userId, Command command) throws IOException, InterruptedException {
        try {
            for (CommandHandler commandHandler : commandHandlers) {
                if (commandHandler.support(command.getNameOfCommand())) {
                    if (!commandHandler.isAux())
                        HistoryManager.getInstance().addHistory(userId, Commands.getShortNameCommand(command));
                    return commandHandler.executeManual(userId, command);
                }
            }
            return new Message(command.getUserUUID(),true, "Unknown command");
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException | SQLException ex) {
            return new Message(command.getUserUUID(),true, ex.toString());
        }
    }

    /**
     * Метод, вызывающий определенную команду (для исполнения скрипта)
     *
     * @throws IOException - если в команде execute_script будет исключение
     */
    public Message executeCommandScript(int userId, Command command, BufferedReader bufferedReader) throws IOException, ScriptUnknownCommandException, InterruptedException {
        try {
            for (CommandHandler commandHandler : commandHandlers) {
                if (commandHandler.support(command.getNameOfCommand())) {
                    if (!commandHandler.isAux()) {
                        HistoryManager.getInstance().addHistory(userId, Commands.getShortNameCommand(command));
                    }
                    return commandHandler.executeScript(userId, command, bufferedReader);
                }
            }
            return new Message(command.getUserUUID(),true, "Unknown command");
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException | SQLException ex) {
            return new Message(command.getUserUUID(),true, ex.toString());
        }
    }

    public void initializeCommands() {
        registerHandler(new ClientExitHandler());
        registerHandler(new CreateNewUserHandler());
        registerHandler(new LogInHandler());
        registerHandler(new CheckConnectionHandler());
        registerHandler(new AddElementCommandHandler());
        registerHandler(new AddIfMaxCommandHandler());
        registerHandler(new ClearCommandHandler());
        registerHandler(new ExecuteScriptCommandHandler());
        registerHandler(new FilterByDistanceCommandHandler());
        registerHandler(new HelpCommandHandler());
        registerHandler(new GroupCountingByNameCommandHandler());
        registerHandler(new HistoryCommandHandler());
        registerHandler(new InfoCommandHandler());
        registerHandler(new PrintDescendingHandler());
        registerHandler(new RemoveGreaterCommandHandler());
        registerHandler(new RemoveByIdCommandHandler());
        registerHandler(new ShowCommandHandler());
        registerHandler(new UpdateIdCommandHandler());
    }
}
