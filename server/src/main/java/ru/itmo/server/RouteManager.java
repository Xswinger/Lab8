package ru.itmo.server;

import ru.itmo.common.dto.Route;
import ru.itmo.server.database.dao.RoutesDao;
import ru.itmo.server.utils.ChangeFieldValue;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class RouteManager {
    private final ReentrantLock reentrantLock = new ReentrantLock();
    private volatile HashSet<Route> routes;
    private static RouteManager instance = null;

    public RouteManager() throws SQLException {
        initializeRoutes();
    }

    public static RouteManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new RouteManager();
        }
        return instance;
    }

    private void initializeRoutes() throws SQLException {
        routes = RoutesDao.getInstance().readAllRoutes();
    }

    public void addRoute(Route route){
        reentrantLock.lock();
        try {
            routes.add(route);
        } finally {
            reentrantLock.unlock();
        }

    }

    public void removeRoutes(Set<Long> setToRemove){
        reentrantLock.lock();
        try {
            routes.removeIf(route -> setToRemove.contains(route.getId()));
        } finally {
            reentrantLock.unlock();
        }
    }

    public void updateRoute(long id, int userId, Route updatedRoute) throws SQLException {
        reentrantLock.lock();
        try {
            if (routes.stream().anyMatch(route -> id == route.getId())) {
                RoutesDao.getInstance().updateRouteById(id, userId, updatedRoute);
                ChangeFieldValue.ChangerFieldValue(routes.stream().filter
                        (route -> id == route.getId()).findFirst().get(), updatedRoute, id);
            }
        } finally {
            reentrantLock.unlock();
        }
    }

    public String getCollectionType() {
        reentrantLock.lock();
        try {
            return routes.getClass().getName();
        }
        finally {
            reentrantLock.unlock();
        }
    }

    public Integer getRoutesCount() {
        reentrantLock.lock();
        try {
            return routes.size();
        }
        finally {
            reentrantLock.unlock();
        }
    }

    public Map<String, Long> groupRoutesByName() {
        reentrantLock.lock();
        try {
            return new ArrayList<>(routes).stream().collect(Collectors.groupingBy(Route::getName, Collectors.counting()));
        }
        finally {
            reentrantLock.unlock();
        }
    }

    public List<Route> getSortedRoutesBySize() {
        reentrantLock.lock();
        try {
            return routes.stream().sorted(Comparator.comparing(Route::getSize)).collect(Collectors.toList());
        }
        finally {
            reentrantLock.unlock();
        }
    }

    public Set<Route> getSortedRoutesByNameLessThen(String name) {
        reentrantLock.lock();
        try {
            return routes.stream().filter(route -> (name).compareTo(route.getName()) < 0).collect(Collectors.toSet());
        }
        finally {
            reentrantLock.unlock();
        }
    }

    public Set<Route> getSortedRoutesByNameMoreThen(String name) {
        reentrantLock.lock();
        try {
            return routes.stream().filter(route -> (name).compareTo(route.getName()) > 0).collect(Collectors.toSet());
        }
        finally {
            reentrantLock.unlock();
        }
    }

    public boolean findMatchByDistance(int distance) {
        reentrantLock.lock();
        try {
            return routes.stream().noneMatch(route -> route.getDistance() == distance);
        }
        finally {
            reentrantLock.unlock();
        }
    }

    public List<Route> filterRoutesByDistance(int distance) {
        reentrantLock.lock();
        try {
            return routes.stream().filter(route -> route.getDistance() == distance).collect(Collectors.toList());
        }
        finally {
            reentrantLock.unlock();
        }
    }
}
