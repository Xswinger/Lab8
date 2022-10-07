package ru.itmo.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.common.dto.Route;

//Меняет поля выбранного объекта на поля отправленного объекта
public class ChangeFieldValue {
    private static final Logger logger = LoggerFactory.getLogger(ChangeFieldValue.class);
    public static void ChangerFieldValue(Route editableRoute, Route newRoute, long id) {
        try {
            if (newRoute.getFrom() != null) {
                editableRoute.setId(id);
                editableRoute.setName(newRoute.getName());
                editableRoute.setCoordinates(newRoute.getCoordinates().getX(), newRoute.getCoordinates().getY());
                editableRoute.setCreationData(newRoute.getCreationDate());
                editableRoute.setFrom(new ru.itmo.common.dto.locations.locationFrom.Location(newRoute.getFrom().getX(), newRoute.getFrom().getY(), newRoute.getFrom().getZ()));
            } else {
                editableRoute.setId(id);
                editableRoute.setName(newRoute.getName());
                editableRoute.setCoordinates(newRoute.getCoordinates().getX(), newRoute.getCoordinates().getY());
                editableRoute.setCreationData(newRoute.getCreationDate());
                editableRoute.setFrom();
            }
            editableRoute.setTo(newRoute.getTo().getX(), newRoute.getTo().getY(), newRoute.getTo().getZ(), newRoute.getTo().getName());
            editableRoute.setDistance(newRoute.getDistance());
            logger.info("Element update successfully");
        } catch (NumberFormatException ex) {
            logger.info("Invalid input variable format");
        }
    }
}
