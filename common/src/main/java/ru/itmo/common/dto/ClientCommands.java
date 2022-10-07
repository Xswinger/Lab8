package ru.itmo.common.dto;

/**
 * Enum для хранения команды и ее описания
 */
public enum ClientCommands {
    ENVIRONMENT("environment"),
    HELP("help"),
    INFO("info"),
    SHOW("show"),
    ADD("add"),
    UPDATE_ID("update id {element}"),
    REMOVE_BY_ID("remove_by_id id"),
    CLEAR("clear"),
    EXECUTE_SCRIPT("execute_script file_name"),
    EXIT("exit"),
    ADD_IF_MAX("add_if_max {element}"),
    REMOVE_GREATER("remove_greater {element}"),
    HISTORY("history"),
    GROUP_COUNTING_BY_NAME("group_counting_by_name"),
    FILTER_BY_DISTANCE("filter_by_distance distance"),
    PRINT_DESCENDING("print_descending");

    private final String commandName;

    /**
     * Конструктор Commands
     *
     * @param commandName - имя команды
     */
    ClientCommands(String commandName) {
        this.commandName = commandName;
    }

    /**
     * Метод для получения имени команды
     *
     * @param nameCommand - имя команды
     * @return Имя команды
     */
    public static String getNameCommand(ClientCommands nameCommand) {
        return nameCommand.commandName;
    }
}
