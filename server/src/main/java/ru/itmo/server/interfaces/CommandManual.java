package ru.itmo.server.interfaces;

import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Message;
import ru.itmo.common.dto.Route;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public interface CommandManual {
    Message executeManual(int userId, Command command) throws IOException, SQLException, InterruptedException;
}
