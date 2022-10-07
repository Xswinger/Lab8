package ru.itmo.server.commandClasses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Message;
import ru.itmo.common.dto.Print;
import ru.itmo.common.dto.Route;
import ru.itmo.server.interfaces.CommandHandler;
import ru.itmo.server.mainClasses.Invoker;
import ru.itmo.server.exceptions.ScriptUnknownCommandException;
import ru.itmo.server.utils.FileUtil;

import java.io.*;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Класс команды execute_script
 */
public class ExecuteScriptCommandHandler implements CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExecuteScriptCommandHandler.class);
    /**
     * Метод execute команды execute_script
     * @return Message[]
     * @throws IOException - все ошибки, возможные при исполнении скрипта
     */
    public Message executeManual(int userId, Command command) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(FileUtil.filePath(command.getParameterOfCommand()));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        final Command[] scriptCommand = new Command[1];
        StringBuilder message = new StringBuilder();
        bufferedReader.lines().forEachOrdered(line -> {
        String[] lineParts = line.split(" ");
            if (lineParts.length == 2) {
                scriptCommand[0] = new Command(command.getUserUUID(),true, command.getLogin(),
                        command.getPassword(), lineParts[0], lineParts[1]);
            } else {
                scriptCommand[0] = new Command(command.getUserUUID(),true, command.getLogin(),
                        command.getPassword(), line.split(" ")[0]);
            }
            try{
                Message requestMessage = Invoker.getInstance().executeCommandScript(userId, scriptCommand[0], bufferedReader);
                String messageString = requestMessage.getContentString();
                List<Route> messageRoute = requestMessage.getContentRoute();
                if (messageString != null) {
                    message.append(messageString).append("\n");
                }
                if (messageRoute != null) {
                    message.append(Print.routeToString(messageRoute));
                }
                message.append("\n");
            } catch (ScriptUnknownCommandException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        logger.warn("Script execution completed");
        return new Message(command.getUserUUID(),true, "Script execution completed\n" + message);
    }

    @Override
    public boolean isAux() {
        return false;
    }

    @Override
    public boolean support(String commandName) {
        return "execute_script".equals(commandName);
    }

    @Override
    public Message executeScript(int userId, Command command, Object... args) throws IOException {
        logger.warn("Execute script command in script");
        return new Message(command.getUserUUID(),true, "Cannot execute script into another script");
    }
}
