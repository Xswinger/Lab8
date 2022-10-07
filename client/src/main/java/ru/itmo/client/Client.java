package ru.itmo.client;

import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Message;
import ru.itmo.common.dto.Print;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.UUID;
import java.util.stream.IntStream;

public class Client {
    private static Client instance = null;
    public static Client getInstance() {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }
    public User checkingUser;
    public UUID getUserUUID() {
        return userUUID;
    }
    private final UUID userUUID = UUID.randomUUID();
    private DatagramChannel datagramChannel;
    private final ByteBuffer BUFFER_RECEIVE = ByteBuffer.allocate(5000);
    private final String HOST = "localhost";
    private final int PORT = 63220;
    private final SocketAddress SERVER_ADDRESS = new InetSocketAddress(HOST, PORT);
    private UUID UUID_LAST_REQUEST;

    //Метод формирования запроса и обработки ответа от сервера
    public Message exchangeMessage(User user, Command sendingCommand) throws IOException, InterruptedException,
            ClassNotFoundException {
        checkingUser = user;
        Message message;
        if (sendingCommand.getNameOfCommand() != null && sendingCommand.getNameOfCommand().equals("error")) {
            System.out.println(sendingCommand);
        }
        if (sendingCommand.getNameOfCommand() != null &&
                (sendingCommand.getNameOfCommand().equals("Unknown command") ||
                sendingCommand.getNameOfCommand().equals("Invalid input variable format for this parameter"))) {
            System.out.println(sendingCommand.getNameOfCommand());
        }
        try {
            message = sendingMessage(sendingCommand);
            UUID_LAST_REQUEST = message.getMessageUUID();
        }
        catch (PortUnreachableException e){
            user.setRegistrationStatus(false);
            throw new IOException("Cannot receive answer from server");
        }
        setRegistrationStatus(message);
        if (message.getContentString() != null &&
                message.getContentString().equals("Exiting...")) {
            System.out.println(Print.printContent(message));
            datagramChannel.disconnect();
            user.setRegistrationStatus(false);
            System.exit(1);
            return null;
        } else {
            return message;
        }
    }

    //Инициализация канала связи
    public void channelInitialize() throws IOException {
        datagramChannel = DatagramChannel.open();
        datagramChannel.connect(SERVER_ADDRESS);
        datagramChannel.configureBlocking(false);
    }

    //Метод установки состояния регистрации
    private void setRegistrationStatus(Message message) {
        if (!checkingUser.isRegistrationStatus() && message.getRegistrationStatus()) {
            checkingUser.setRegistrationStatus(true);
        }
    }

    //Запись запроса и получение ответного пакета
    private Message sendingMessage(Command sendingClass) throws IOException, InterruptedException,
            ClassNotFoundException {
        BUFFER_RECEIVE.clear();
        ByteBuffer bufferSend = ByteBuffer.wrap(Command.serialize(sendingClass));
        datagramChannel.write(bufferSend);
        Message message = waitingReceive();
        return message;
    }

    //Цикл ожидания ответного сообщения
    private Message waitingReceive() throws IOException, ClassNotFoundException, InterruptedException {
        Thread.sleep(50);
        while (IntStream.range(0, BUFFER_RECEIVE.array().length).parallel().allMatch(j ->
            BUFFER_RECEIVE.array()[j] == 0)) {
            datagramChannel.read(BUFFER_RECEIVE);
            Thread.sleep(50);
        }
        while (((Message) Message.deserialize(BUFFER_RECEIVE.array())).getMessageUUID().equals(UUID_LAST_REQUEST)) {
            datagramChannel.read(BUFFER_RECEIVE);
            Thread.sleep(50);
        }
        return (Message) Message.deserialize(BUFFER_RECEIVE.array());
    }
}
