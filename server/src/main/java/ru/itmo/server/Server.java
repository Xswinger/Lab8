package ru.itmo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Message;
import ru.itmo.server.database.dao.UsersDao;
import ru.itmo.server.mainClasses.Invoker;

import java.io.IOException;
import java.net.*;
import java.sql.SQLException;
import java.util.concurrent.*;

public class Server {
    private final ExecutorService threadPoolExecutor = ThreadPoolManager.getExecutor();
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private final DatagramSocket datagramSocket;
    private final byte[] inputDataBuffer;

    public Server(int port) {
        try {
            datagramSocket = new DatagramSocket(port);
            inputDataBuffer = new byte[4096];
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    //Получение команды, ее обработка и отправка назад
    public void start() throws IOException{
        while (true) {
            DatagramPacket inputPacket = new DatagramPacket(inputDataBuffer, inputDataBuffer.length);
            datagramSocket.receive(inputPacket);
            Runnable runnable = () -> {
                try {
                    handleRequest(inputPacket, threadPoolExecutor);
                } catch (IOException | ClassNotFoundException | SQLException | InterruptedException |
                         ExecutionException e) {
                    throw new RuntimeException(e);
                }
            };
            new Thread(runnable).start();
        }
    }

    private void handleRequest(DatagramPacket inputPacket, ExecutorService threadPoolExecutor) throws IOException, ClassNotFoundException, SQLException, ExecutionException, InterruptedException {
        InetAddress senderAddress = inputPacket.getAddress();
        int clientPort = inputPacket.getPort();
        Command command = (Command) Command.deserialize(inputPacket.getData());
        logger.info("New request received from the client at address: {}, port: {}", senderAddress.getHostAddress(), clientPort);

        // Handle command and send result message
        Callable<Message> processingRequest = () -> {
            try {
                return Invoker.getInstance().executeCommandManual(
                        UsersDao.getInstance().checkUser(command.getLogin(), command.getPassword()), command);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        };
        Future<Message> futureRequest = threadPoolExecutor.submit(processingRequest);
        Message message = futureRequest.get();
        Callable<Message> sendingResponse = () -> {
            try {
                byte[] sendingDataBuffer = Message.serialize(message);
                DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length,
                        senderAddress, clientPort);
                logger.info("Sending a response");
                datagramSocket.send(sendingPacket);
                logger.info("Waiting new receive...");
                return null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        threadPoolExecutor.submit(sendingResponse);
    }
}
