package ru.itmo.common.dto;

import ru.itmo.common.dto.locations.locationTo.Location;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

import java.io.*;
import java.util.Date;
import java.util.Objects;

/**
 * Класс маршрута со свойствами <b>id</b>, <b>name</b>, <b>coordinates</b>, <b>creationDate</b>, <b>from</b>, <b>to</b>, <b>distance</b>, .
 *
 * @author Игорь Аллаяров
 * @version 0.1
 */

public class Route implements Serializable, Comparable {

    /**
     * Поле id
     * Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
     */
    private long id;

    /**
     * Поле name
     * Поле не может быть null, Строка не может быть пустой
     */
    private String name;

    /**
     * Класс Coordinates
     * Поле не может быть null
     */
    private Coordinates coordinates;

    /**
     * Поле creationDate
     * Поле не может быть null, Значение этого поля должно генерироваться автоматически
     */
    private Date creationDate;

    /**
     * Класс Location
     * Поле может быть null
     */
    private ru.itmo.common.dto.locations.locationFrom.Location from;

    /**
     * Класс Location
     * Поле не может быть null
     */
    private Location to;

    /**
     * Поле distance
     * Значение поля должно быть больше 1
     */
    private int distance;

    /**
     * Конструктор экземпляра класса Route без аргументов
     *
     * @see Route#Route(long, String, Coordinates, Date, ru.itmo.common.dto.locations.locationFrom.Location, Location, int)
     * @see Route#Route(long, String, Coordinates, Date, Location, int)
     */
    public Route() {
    }

    /**
     * Конструктор экземпляра класса Route, получающий аргументы id, name, coordinates, to, distance
     *
     * @param id          - id элемента
     * @param name        - имя элемента
     * @param coordinates - координаты элемента
     * @param to          - поле to элемента
     * @param distance    - дистанция элемента
     * @see Route#Route(long, String, Coordinates, Date, ru.itmo.common.dto.locations.locationFrom.Location, Location, int)
     */
    public Route(long id, String name, Coordinates coordinates, Date localDate, Location to, int distance) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = localDate;
        this.from = null;
        this.to = to;
        this.distance = distance;
    }

    /**
     * Конструктор экземпляра класса Route, получающий аргументы id, name, coordinates, from, to, distance
     *
     * @param id          -id элемента
     * @param name        - имя элемента
     * @param coordinates - координаты элемента
     * @param from        - поле from элемента
     * @param to          - поле to элемента
     * @param distance    - дистанция элемента
     * @see Route#Route(long, String, Coordinates, Date, Location, int)
     */
    public Route(long id, String name, Coordinates coordinates, Date localDate, ru.itmo.common.dto.locations.locationFrom.Location from, Location to, int distance) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = localDate;
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    /**
     * Метод проверки поля name
     *
     * @param name - поле name
     * @return boolean
     */
    public static String checkName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            return name;
        } else {
            throw new NumberFormatException();
        }
    }

    /**
     * Метод проверки поля creationDate
     *
     * @param localDate - поле creationDate
     * @return localDate
     */
    public static Date checkData(Date localDate) {
        if (localDate != null) {
            return localDate;
        } else {
            throw new NumberFormatException();
        }
    }


    /**
     * Метод проверки поля location
     *
     * @param location - поле
     * @return location
     */
    public static Location checkLocationTo(Location location) {
        if (location != null) {
            return location;
        } else {
            throw new NumberFormatException();
        }
    }

    /**
     * Метод проверки поля distance
     *
     * @param distance - поле distance
     * @return boolean
     */
    public static int checkDistance(int distance) {
        if (distance > 1) {
            return distance;
        } else {
            throw new NumberFormatException();
        }
    }

    /**
     * Метод получения id
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Метод установки id
     *
     * @param id - поле id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Метод получения name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Метод установки name
     *
     * @param name - поле name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Метод получения поля coordinates
     *
     * @return x
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Метод установки полей x и y объекта coordinates
     *
     * @param x - поле x объекта coordinates
     * @param y - поле y объекта coordinates
     */
    public void setCoordinates(long x, Integer y) {
        this.coordinates.setX(x);
        this.coordinates.setY(y);
    }

    /**
     * Метод получения поля creationDate
     *
     * @return creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Метод установки поля
     *
     * @param creationDate - поле даты создания элемента
     */

    public void setCreationData(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Метод получения поля from
     *
     * @return x
     */
    public ru.itmo.common.dto.locations.locationFrom.Location getFrom() {
        return from;
    }

    public void setFrom(ru.itmo.common.dto.locations.locationFrom.Location from) {
        this.from = from;
    }

    public void setFrom() {
        this.from = null;
    }

    /**
     * Метод получения поля to
     *
     * @return x
     */
    public Location getTo() {
        return to;
    }

    /**
     * Метод установки полей x, y, z и name объекта to
     *
     * @param x    - поле x объекта to
     * @param y    - поле y объекта to
     * @param z    - поле z объекта to
     * @param name - поле name объекта to
     */
    public void setTo(Integer x, long y, float z, String name) {
        to.setX(x);
        to.setY(y);
        to.setZ(z);
        to.setName(name);
    }

    /**
     * Метод получения distance
     *
     * @return distance
     */
    public int getDistance() {
        return distance;
    }


    /**
     * Метод установки distance
     *
     * @param distance - поле distance
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Object o) {
        Route route = (Route) o;
        if (this.id < route.id) {
            return -1;
        } else if (this.id > (route).id) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return id == route.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    public long getSize() {
        return ObjectSizeCalculator.getObjectSize(this);
    }
}
