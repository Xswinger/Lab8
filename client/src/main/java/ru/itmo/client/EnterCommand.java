package ru.itmo.client;

import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Route;

import java.util.Arrays;

public class EnterCommand {
    //Выбор создания запроса команды или получения файла
    public static Command LogInCommand(User user) {
        if (user.isCreateNewUser()) {
            return new Command(Client.getInstance().getUserUUID(), true, false, user.getName(),
                    user.getPassword(), "creating new user");
        }
        else {return new Command(Client.getInstance().getUserUUID(),false, false, user.getName(),
                user.getPassword(), "registration");
        }
    }

    //передавать как enum имя команды
    public static Command formCommand(User user, String inputCommand) {
        try {
            Command command;
            if (inputCommand.split(" ").length == 2) {
                command = new Command(Client.getInstance().getUserUUID(),true, user.getName(),
                        user.getPassword(), inputCommand.split(" ")[0], inputCommand.split(" ")[1]);
            } else {
                command = new Command(Client.getInstance().getUserUUID(),true, user.getName(),
                        user.getPassword(), inputCommand.split(" ")[0]);
            }
            if (Command.checkCommand(command.getNameOfCommand())) {
                if (Arrays.stream(new String[]{"add", "add_if_max", "remove_greater"}).anyMatch(s ->
                        s.equals(command.getNameOfCommand()))) {
                    return new Command(Client.getInstance().getUserUUID(),true, user.getName(),
                            user.getPassword(), command.getNameOfCommand(), (Route) null);
                } else if (command.getNameOfCommand().equals("update")) {
                    return new Command(Client.getInstance().getUserUUID(),true, user.getName(),
                            user.getPassword(), command.getNameOfCommand(),
                            command.getParameterOfCommand(), null);
                } else {
                    return command;
                }
            } else {
                return new Command(Client.getInstance().getUserUUID(),false, user.getName(), user.getPassword(),
                        "Unknown command", "Unknown command");
            }
        } catch (NumberFormatException ex) {
            return new Command(Client.getInstance().getUserUUID(),false, user.getName(), user.getPassword(),
                    "Invalid input variable format for this parameter");
        }
    }
}
