package ru.itmo.common.dto;

import java.io.*;
import java.util.Arrays;
import java.util.UUID;

/**
 * Объект для передачи команд и их аргументов
 */
public class Command implements Serializable {
    private final UUID userUUID;
    private boolean creatingNewUser;
    private boolean checkRegistration;
    private String login;
    private String password;
    private String nameOfCommand;
    private String parameterOfCommand;
    private Route routeOfCommand;

    public Command(UUID userUUID){this.userUUID = userUUID;}

    public Command(UUID userUUID, boolean creatingNewUser, boolean checkRegistration, String login, String password){
        this.userUUID = userUUID;
        this.creatingNewUser = creatingNewUser;
        this.checkRegistration = checkRegistration;
        this.login = login;
        this.password = password;
    }

    public  Command(UUID userUUID, boolean checkRegistration, String login, String password, String nameOfCommand, String parameterOfCommand, Route routeOfCommand) {
        this.userUUID = userUUID;
        this.checkRegistration = checkRegistration;
        this.login = login;
        this.password = password;
        this.nameOfCommand = nameOfCommand;
        this.parameterOfCommand = parameterOfCommand;
        this.routeOfCommand = routeOfCommand;
    }

    public Command(UUID userUUID, boolean checkRegistration, String login, String password, String nameOfCommand, String parameterOfCommand) {
        this.userUUID = userUUID;
        this.checkRegistration = checkRegistration;
        this.login = login;
        this.password = password;
        this.nameOfCommand = nameOfCommand;
        this.parameterOfCommand = parameterOfCommand;
    }

    public Command(UUID userUUID, boolean creatingNewUser, boolean checkRegistration, String login, String password, String nameOfCommand) {
        this.userUUID = userUUID;
        this.creatingNewUser = creatingNewUser;
        this.checkRegistration = checkRegistration;
        this.login = login;
        this.password = password;
        this.nameOfCommand = nameOfCommand;
        this.parameterOfCommand = null;
    }

    public Command(UUID userUUID, boolean checkRegistration, String login, String password, String nameOfCommand) {
        this.userUUID = userUUID;
        this.checkRegistration = checkRegistration;
        this.login = login;
        this.password = password;
        this.nameOfCommand = nameOfCommand;
        this.parameterOfCommand = null;
    }

    public Command(UUID userUUID, boolean checkRegistration, String login, String password, String nameOfCommand, Route parameterOfCommand) {
        this.userUUID = userUUID;
        this.checkRegistration = checkRegistration;
        this.login = login;
        this.password = password;
        this.nameOfCommand = nameOfCommand;
        this.routeOfCommand = parameterOfCommand;
    }

    public boolean getRegistrationCheck() {
        return checkRegistration;
    }

    public void setLoginCheck(boolean loginCheck) {
        this.checkRegistration = loginCheck;
    }

    public Route getRouteOfCommand() {
        return routeOfCommand;
    }

    public String getNameOfCommand() {
        return nameOfCommand;
    }

    public String getParameterOfCommand() {
        return parameterOfCommand;
    }

    public void setRouteOfCommand(Route routeOfCommand) {
        this.routeOfCommand = routeOfCommand;
    }

    public void setNameOfCommand(String nameOfCommand) {
        this.nameOfCommand = nameOfCommand;
    }

    public void setParameterOfCommand(String parameterOfCommand) {
        this.parameterOfCommand = parameterOfCommand;
    }

    public static byte[] serialize(Command command) throws IOException {
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
             ObjectOutputStream objStream = new ObjectOutputStream(byteStream)) {
            objStream.writeObject(command);
            return byteStream.toByteArray();
        }
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
             ObjectInputStream objStream = new ObjectInputStream(byteStream);) {
            return objStream.readObject();
        }
    }

    public static boolean checkCommand(String commandName) {
        return Arrays.stream(ClientCommands.values()).anyMatch(commands -> true);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getCreatingNewUser() {
        return creatingNewUser;
    }

    public void setCreatingNewUser(boolean creatingNewUser) {
        this.creatingNewUser = creatingNewUser;
    }

    public UUID getUserUUID() {
        return userUUID;
    }
}
