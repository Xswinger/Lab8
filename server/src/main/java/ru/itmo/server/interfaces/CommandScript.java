package ru.itmo.server.interfaces;

import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Message;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface CommandScript {
    boolean support(String commandName);
    Message executeScript(int userId, Command command, Object... args) throws IOException, SQLException, InterruptedException;
}
