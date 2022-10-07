package ru.itmo.server.utils;

import ru.itmo.common.dto.Route;
import ru.itmo.server.database.dao.RoutesDao;

import java.sql.SQLException;
import java.util.Date;

//Класс генерации полей в элементы коллекции
public class AssignmentOfAutomaticallyGeneratedFields {
    public static Route generate(Route route, long id) throws SQLException {
        route.setId(id);
        route.setCreationData(new Date());
        return route;
    }
}
