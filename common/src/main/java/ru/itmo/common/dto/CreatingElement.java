package ru.itmo.common.dto;

import ru.itmo.common.dto.locations.locationFrom.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class CreatingElement {
    //Создание элемента коллекции через терминал
    public static Route manualCreatingElement(List<Object> list) {
        String name = Route.checkName((String) list.get(0));
        Coordinates coordinates = new Coordinates(Coordinates.checkX((Long.parseLong((String) list.get(1)))),
                Coordinates.checkY(Integer.parseInt((String) list.get(2))));
        Date creationDate = new Date();
        Location from = null;
        Object X = list.get(3);
        if (!X.toString().equals("")) {
            from = new Location(Float.parseFloat(X.toString()), Double.parseDouble((String) list.get(4)),
                    Integer.parseInt((String) list.get(5)));
        }
        ru.itmo.common.dto.locations.locationTo.Location to = new
                ru.itmo.common.dto.locations.locationTo.Location(
                        ru.itmo.common.dto.locations.locationTo.Location.checkX(
                                (Integer.parseInt((String) list.get(6)))),
                Long.parseLong((String) list.get(7)), Float.parseFloat((String) list.get(8)),
                Route.checkName((String) list.get(9)));
        int distance = Route.checkDistance(Integer.parseInt((String) list.get(10)));
        return checkFrom(from, X, name, coordinates, creationDate, to, distance);
    }

    //Создание элемента коллекции через скрипт
    public static Route scriptCreatingElement(BufferedReader bufferedReader) throws IOException {
        String bufferName = bufferedReader.readLine();
        String bufferCoordinateX = bufferedReader.readLine();
        String bufferCoordinateY = bufferedReader.readLine();
        String bufferFromX = bufferedReader.readLine();
        String bufferFromY = "";
        String bufferFromZ = "";
        Location from = null;
        if (!((Object) bufferFromX).toString().equals("")) {
            bufferFromY = bufferedReader.readLine();
            bufferFromZ = bufferedReader.readLine();
        }
        String bufferToX = bufferedReader.readLine();
        String bufferToY = bufferedReader.readLine();
        String bufferToZ = bufferedReader.readLine();
        String bufferToName = bufferedReader.readLine();
        String bufferDistance = bufferedReader.readLine();
        String name = Route.checkName(bufferName);
        Coordinates coordinates = new Coordinates(Coordinates.checkX(Long.parseLong(bufferCoordinateX)),
                Coordinates.checkY(Integer.parseInt(bufferCoordinateY)));
        Date creationDate = new Date();
        if (!((Object) bufferFromX).toString().equals("")) {
            from = new Location(Float.parseFloat(((Object) bufferFromX).toString()), Double.parseDouble(bufferFromY),
                    Integer.parseInt(bufferFromZ));
        }
        ru.itmo.common.dto.locations.locationTo.Location to = new
                ru.itmo.common.dto.locations.locationTo.Location(
                        ru.itmo.common.dto.locations.locationTo.Location.checkX(Integer.parseInt(bufferToX)),
                Long.parseLong(bufferToY), Float.parseFloat(bufferToZ), Route.checkName(bufferToName));
        int distance = Route.checkDistance(Integer.parseInt(bufferDistance));
        return checkFrom(from, bufferFromX, name, coordinates, creationDate, to, distance);
    }

    private static Route checkFrom(Location from, Object x, String name, Coordinates coordinates, Date creationDate,
                                   ru.itmo.common.dto.locations.locationTo.Location to, int distance) {
        Route addedRoute;
        if ("none".equals(x.toString())) {
            addedRoute = new Route(-1, name, coordinates, creationDate, to, distance);
        } else {
            addedRoute = new Route(-1, name, coordinates, creationDate, from, to, distance);
        }
        return addedRoute;
    }

}
