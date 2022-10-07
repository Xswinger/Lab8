package ru.itmo.common.dto;

import java.io.*;
import java.util.List;
import java.util.UUID;

//Класс для сообщений от сервера
public class Message implements Serializable {
    private final UUID userUUID;
    private final UUID messageUUID;
    private boolean registrationStatus;
    private String contentString;

    private List<Route> contentRoute;

    public Message(UUID userUUID, boolean registrationStatus, String contentString) {
        this.userUUID = userUUID;
        this.messageUUID = UUID.randomUUID();
        this.registrationStatus = registrationStatus;
        this.contentString = contentString;
    }

    public Message(UUID userUUID, boolean registrationStatus, List<Route> contentRoute) {
        this.userUUID = userUUID;
        this.messageUUID = UUID.randomUUID();
        this.registrationStatus = registrationStatus;
        this.contentRoute = contentRoute;
    }

    public Message(UUID userUUID, boolean registrationStatus, String contentString, List<Route> contentRoute) {
        this.userUUID = userUUID;
        this.messageUUID = UUID.randomUUID();
        this.registrationStatus = registrationStatus;
        this.contentString = contentString;
        this.contentRoute = contentRoute;
    }

    public UUID getMessageUUID() {
        return messageUUID;
    }

    public List<Route> getContentRoute() {
        return contentRoute;
    }

    public String getContentString() {
        return contentString;
    }

    public void setContentString(String contentString) {
        this.contentString = contentString;
    }

    public void setContentRoute(List<Route> contentRoute) {
        this.contentRoute = contentRoute;
    }

    public static byte[] serialize(Message message) throws IOException {
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
             ObjectOutputStream objStream = new ObjectOutputStream(byteStream)) {
            objStream.writeObject(message);
            return byteStream.toByteArray();
        }
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
             ObjectInputStream objStream = new ObjectInputStream(byteStream)) {
            return objStream.readObject();
        }
    }

    public boolean getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(boolean registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public UUID getUserUUID() {
        return userUUID;
    }
}
