package ru.itmo.server;

import ru.itmo.common.dto.Command;

import java.util.Arrays;

/**
 * Enum для хранения команды и ее описания
 */

public enum Commands {
    REGISTRATION("registration", "registration"),
    CREATING_NEW_USER("creating new user", "creating new user"),
    EXIT("exit", "client exiting"),
    CHECK("check", "check_connection"),
    HELP("help", "display help on available commands"),
    INFO("info", "print information about the collection to the standard output stream " +
            "(type, initialization date, number of elements, etc.)"),
    SHOW("show", "print to standard output all elements of the collection in string " +
            "representation"),
    ADD("add {element}", "add", "add a new element to the collection"),
    UPDATE_ID("update id {element}", "update",
            "update the value of the collection element whose id is equal to the given one"),
    REMOVE_BY_ID("remove_by_id id","remove_by_id",
            "remove element from collection by its id"),
    CLEAR("clear", "clear the collection"),
    EXECUTE_SCRIPT("execute_script file_name","execute_script",
            "read and execute the script from the " +
            "specified file. The script contains commands in the same form in which they are entered by the user in " +
            "interactive mode"),
    ADD_IF_MAX("add_if_max {element}", "add_if_max",
            "add a new element to the collection if its " +
            "value is greater than the value of the largest element in this collection"),
    REMOVE_GREATER("remove_greater {element}", "remove_greater",
            "remove from the collection all elements greater than the given"),
    HISTORY("history", "print the last 12 commands (without their arguments)"),
    GROUP_COUNTING_BY_NAME("group_counting_by_name", "group the elements of the " +
            "collection by the value of the name field, display the number of elements in each group"),
    FILTER_BY_DISTANCE("filter_by_distance distance", "filter_by_distance",
            "display elements whose distance " +
            "field value is equal to the given one"),
    PRINT_DESCENDING("print_descending", "display the elements of the collection in " +
            "descending order");

    private final String commandFullName;
    private final String commandShortName;
    private final String commandsDescription;

    /**
     * Конструктор Commands
     * @param commandShortName - короткое имя команды
     * @param commandFullName - полное имя команды
     * @param commandsDescription - описание команды
     */
    Commands(String commandFullName, String commandShortName, String commandsDescription) {
        this.commandFullName = commandFullName;
        this.commandShortName = commandShortName;
        this.commandsDescription = commandsDescription;
    }

    Commands(String commandShortName, String commandsDescription) {
        this.commandFullName = commandShortName;
        this.commandShortName = commandShortName;
        this.commandsDescription = commandsDescription;
    }

    /**
     * Метод для получения имени команды
     *
     * @param nameCommand - имя команды
     * @return Имя команды
     */
    public static String getFullNameCommand(Commands nameCommand) {
        return nameCommand.commandFullName;
    }

    public static String getShortNameCommand(Command command) {
        return Arrays.stream(Commands.values()).filter(commands ->
                commands.commandShortName.equals(command.getNameOfCommand())).findFirst().get().commandShortName;
    }

    /**
     * Метод для получения описания команды
     *
     * @param command - описание команды
     * @return Описание команды
     */
    public static String getDescription(Commands command) {
        return command.commandsDescription;
    }
}
