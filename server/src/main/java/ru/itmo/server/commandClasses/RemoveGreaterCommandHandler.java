package ru.itmo.server.commandClasses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Message;
import ru.itmo.common.dto.Route;
import ru.itmo.server.RouteManager;
import ru.itmo.server.database.dao.RoutesDao;
import ru.itmo.server.interfaces.CommandHandler;
import ru.itmo.common.dto.CreatingElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Класс команды remove_greater
 */
public class RemoveGreaterCommandHandler implements CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(RemoveGreaterCommandHandler.class);

    /**
     * Метод execute команды remove_greater
     *
     * @return Message[]
     */
    @Override
    public Message executeManual(int userId, Command command) throws SQLException, InterruptedException {
        Set<Long> routeToRemove = new HashSet<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Callable<Set<Long>> getRouteToRemove = () -> {
            RouteManager.getInstance().getSortedRoutesByNameMoreThen(command.getRouteOfCommand().getName()).
                    forEach(route -> routeToRemove.add(route.getId()));
            return routeToRemove;
        };
        Callable<Set<Long>> getUserRoute = () -> RoutesDao.getInstance().FindRoutesOfUser(userId);
        List<Callable<Set<Long>>> futureList = new ArrayList<>();
        futureList.add(getRouteToRemove);
        futureList.add(getUserRoute);
        executorService.invokeAll(futureList);
        Set<Long> userRoute = RoutesDao.getInstance().FindRoutesOfUser(userId);
        routeToRemove.retainAll(userRoute);
        logger.info("Command completed");
        return getMessages(userId, routeToRemove, command);
    }

    /**
     * Метод execute команды remove_greater
     */
    @Override
    public Message executeScript(int userId, Command command, Object... args) throws IOException, SQLException, InterruptedException {
        BufferedReader bufferedReader = (BufferedReader) args[0];
        Route addedRoute = CreatingElement.scriptCreatingElement(bufferedReader);
        ExecutorService executorService = Executors.newCachedThreadPool();
        Set<Long> routeToRemove = new HashSet<>();
        Callable<Set<Long>> getRouteToRemove = () -> {
            RouteManager.getInstance().getSortedRoutesByNameMoreThen(addedRoute.getName()).
                    forEach(route -> routeToRemove.add(route.getId()));
            return routeToRemove;
        };
        Callable<Set<Long>> getUserRoute = () -> RoutesDao.getInstance().FindRoutesOfUser(userId);
        List<Callable<Set<Long>>> futureList = new ArrayList<>();
        futureList.add(getRouteToRemove);
        futureList.add(getUserRoute);
        executorService.invokeAll(futureList);
        Set<Long> userRoute = RoutesDao.getInstance().FindRoutesOfUser(userId);
        routeToRemove.retainAll(userRoute);
        logger.info("Command completed");
        return getMessages(userId, routeToRemove, command);
    }

    private Message getMessages(int userId, Set<Long> setToRemove, Command command) throws SQLException {
        Set<Long> removedId = new HashSet<>(setToRemove);
        for (long routeId : removedId) {
            RoutesDao.getInstance().removeRoute(routeId, userId);
        }
        if (setToRemove.size() != 0) {
            RouteManager.getInstance().removeRoutes(removedId);
            logger.info("Command completed: removal completed successfully");
            return new Message(command.getUserUUID(),true, "Removal completed successfully");
        } else {
            logger.info("Command completed: no element removed");
            return new Message(command.getUserUUID(),true, "No element removed");
        }
    }

    @Override
    public boolean isAux() {
        return false;
    }

    @Override
    public boolean support(String commandName) {
        return "remove_greater".equals(commandName);
    }
}
