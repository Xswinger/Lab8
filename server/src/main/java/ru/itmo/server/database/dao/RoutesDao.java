package ru.itmo.server.database.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.common.dto.Coordinates;
import ru.itmo.common.dto.locations.locationTo.Location;
import ru.itmo.common.dto.Route;
import ru.itmo.server.database.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
public class RoutesDao extends AbstractDao {
    private static final Logger logger = LoggerFactory.getLogger(RoutesDao.class);
    private static RoutesDao instance = null;

    public static RoutesDao getInstance() {
        if (instance == null) {
            instance = new RoutesDao();
        }
        return instance;
    }

    private RoutesDao() {}

    public HashSet<Route> readAllRoutes() throws SQLException {
        logger.info("Reading routes data from a database");
        try {
            HashSet<Route> hashSet = new HashSet<>();
            ResultSet resultSet = getDatabaseConnector().createStatement()
                    .executeQuery("SELECT * FROM routes");
            while (resultSet.next()){
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                long coordinateX = resultSet.getLong("coordinate_x");
                Integer coordinateY = resultSet.getInt("coordinate_y");
                Date date = resultSet.getDate("creation_date");
                float fromX = resultSet.getFloat("from_x");
                double fromY = resultSet.getDouble("from_y");
                Integer fromZ = resultSet.getInt("from_z");
                Integer toX = resultSet.getInt("to_x");
                long toY = resultSet.getLong("to_y");
                float toZ = resultSet.getFloat("to_z");
                String toName = resultSet.getString("to_name");
                int distance = resultSet.getInt("distance");

                if (resultSet.getObject("from_x") != null) {
                    hashSet.add(new Route(id, Route.checkName(name),
                        new Coordinates(Coordinates.checkX(coordinateX),
                                Coordinates.checkY(coordinateY)), Route.checkData(date),
                        new ru.itmo.common.dto.locations.locationFrom.Location(fromX,
                                fromY,
                                ru.itmo.common.dto.locations.locationFrom.Location.checkZ(fromZ)),
                        new Location(Location.checkX(toX),
                                toY, toZ, Location.checkName(toName)),
                        Route.checkDistance(distance)));
                } else {
                    hashSet.add(new Route(id, Route.checkName(name),
                        new Coordinates(Coordinates.checkX(coordinateX),
                                Coordinates.checkY(coordinateY)), Route.checkData(date),
                        null,
                        new Location(Location.checkX(toX),
                                toY, toZ, toName),
                        Route.checkDistance(distance)));
                }
            }
            logger.info("Routes data read successfully");
            return hashSet;
        } catch (NumberFormatException ex) {
            logger.warn("Invalid input variable format");
            return null;
        } catch (IndexOutOfBoundsException | DateTimeParseException ex) {
            logger.warn("Invalid input string format in file");
            return null;
        }
    }
    public Set<Long> FindRoutesOfUser(int userId) throws SQLException {
        Set<Long> removedRoutes = new HashSet<>();
        PreparedStatement preparedStatement = getDatabaseConnector()
                .createPreparedStatement("SELECT * FROM users_routes_map WHERE user_id = ?");
        preparedStatement.setInt(1, userId);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            removedRoutes.add(resultSet.getLong("route_id"));
        }
        return removedRoutes;
    }
    public void updateRouteById(long id, int userId, Route updatedRoute) throws SQLException {
        PreparedStatement preparedStatement;
        if (updatedRoute.getFrom() != null) {
            preparedStatement = getDatabaseConnector()
                    .createPreparedStatement(
                            "UPDATE routes SET (name, coordinate_x, coordinate_y, from_x, from_y, from_z, " +
                            "to_x, to_y, to_z, to_name, distance) = (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) WHERE id = ?" +
                            " AND id IN (SELECT urm.route_id FROM users_routes_map urm WHERE urm.user_id = ?)");
            preparedStatement.setFloat(4, updatedRoute.getFrom().getX());
            preparedStatement.setDouble(5, updatedRoute.getFrom().getY());
            preparedStatement.setInt(6, updatedRoute.getFrom().getZ());
            preparedStatement.setInt(7, updatedRoute.getTo().getX());
            preparedStatement.setLong(8, updatedRoute.getTo().getY());
            preparedStatement.setFloat(9, updatedRoute.getTo().getZ());
            preparedStatement.setString(10, updatedRoute.getTo().getName());
            preparedStatement.setInt(11, updatedRoute.getDistance());
            preparedStatement.setLong(12, id);
            preparedStatement.setInt(13, userId);
        } else {
            preparedStatement = getDatabaseConnector()
                    .createPreparedStatement(
                            "UPDATE routes SET (name, coordinate_x, coordinate_y, from_x, from_y, from_z, " +
                            "to_x, to_y, to_z, to_name, distance) = (?, ?, ?, null, null, null, ?, ?, ?, ?, ?) " +
                            "WHERE id = ? AND id IN " +
                            "(SELECT urm.route_id FROM users_routes_map urm WHERE urm.user_id = ?)"

                    );
            preparedStatement.setInt(4, updatedRoute.getTo().getX());
            preparedStatement.setLong(5, updatedRoute.getTo().getY());
            preparedStatement.setFloat(6, updatedRoute.getTo().getZ());
            preparedStatement.setString(7, updatedRoute.getTo().getName());
            preparedStatement.setInt(8, updatedRoute.getDistance());
            preparedStatement.setLong(9, id);
            preparedStatement.setInt(10, userId);
        }
        preparedStatement.setString(1, updatedRoute.getName());
        preparedStatement.setLong(2, updatedRoute.getCoordinates().getX());
        preparedStatement.setInt(3, updatedRoute.getCoordinates().getY());
        preparedStatement.executeUpdate();
        logger.info("Route with id {} update successfully", id);
    }
    public void addRoute(int userId, long routeId, Route addedRoute) throws SQLException {
        PreparedStatement preparedStatementRoutes;
        if (addedRoute.getFrom() == null) {
            preparedStatementRoutes = getDatabaseConnector()
                    .createPreparedStatement("INSERT INTO routes (id, name, coordinate_x, coordinate_y, " +
                                    "creation_date, from_x, from_y, from_z, to_x, to_y, to_z, to_name, distance) " +
                                    "VALUES (?, ?, ?, ?, ?, null, null, null, ?, ?, ?, ?, ?)");
            preparedStatementRoutes.setInt(6, addedRoute.getTo().getX());
            preparedStatementRoutes.setLong(7, addedRoute.getTo().getY());
            preparedStatementRoutes.setFloat(8, addedRoute.getTo().getZ());
            preparedStatementRoutes.setString(9, addedRoute.getTo().getName());
            preparedStatementRoutes.setInt(10, addedRoute.getDistance());

        } else {
            preparedStatementRoutes = DatabaseConnector.getInstance()
                    .createPreparedStatement("INSERT INTO routes (id, name, coordinate_x, coordinate_y, " +
                                    "creation_date, from_x, from_y, from_z, to_x, to_y, to_z, to_name, distance) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatementRoutes.setFloat(6, addedRoute.getFrom().getX());
            preparedStatementRoutes.setDouble(7, addedRoute.getFrom().getY());
            preparedStatementRoutes.setInt(8, addedRoute.getFrom().getZ());
            preparedStatementRoutes.setInt(9, addedRoute.getTo().getX());
            preparedStatementRoutes.setLong(10, addedRoute.getTo().getY());
            preparedStatementRoutes.setFloat(11, addedRoute.getTo().getZ());
            preparedStatementRoutes.setString(12, addedRoute.getTo().getName());
            preparedStatementRoutes.setInt(13, addedRoute.getDistance());
        }
        preparedStatementRoutes.setLong(1, routeId);
        preparedStatementRoutes.setString(2, addedRoute.getName());
        preparedStatementRoutes.setLong(3, addedRoute.getCoordinates().getX());
        preparedStatementRoutes.setInt(4, addedRoute.getCoordinates().getY());
        preparedStatementRoutes.setObject(5, LocalDate.now());
        preparedStatementRoutes.execute();
        logger.info("Route add successfully");

        PreparedStatement preparedStatementMap = getDatabaseConnector()
                .createPreparedStatement("INSERT INTO users_routes_map (id, user_id, route_id) VALUES (?, ?, ?)");
        preparedStatementMap.setInt(1, Math.toIntExact(routeId));
        preparedStatementMap.setInt(2, userId);
        preparedStatementMap.setLong(3, routeId);
        preparedStatementMap.execute();
        logger.info("Map add successfully");
    }
    public void removeRoute(long routeId, int userId) throws SQLException {
        PreparedStatement preparedStatementForMap;
        preparedStatementForMap = getDatabaseConnector()
                .createPreparedStatement("DELETE FROM users_routes_map WHERE route_id = ?");
        preparedStatementForMap.setLong(1, routeId);
        preparedStatementForMap.executeUpdate();

        PreparedStatement preparedStatementForRoutes;
        preparedStatementForRoutes = getDatabaseConnector()
                .createPreparedStatement("DELETE FROM routes WHERE id = ?");
        preparedStatementForRoutes.setLong(1, routeId);
        preparedStatementForRoutes.executeUpdate();
        logger.info("Routes owned by a user with id {} remove successfully", userId);
    }
    @Override
    protected String getSeqName() {
        return "routes_id_seq";
    }
}

