package ru.itmo.common.dto.locations.locationTo;

import java.io.Serializable;

/**
 * Класс Location
 */
public class Location implements Serializable {
    /**
     * Координата x
     * Поле не может быть null
     */
    private Integer x;
    /**
     * Координата y
     */
    private long y;
    /**
     * Координата z
     */
    private float z;
    /**
     * Поле name
     * Строка не может быть пустой, Поле может быть null
     */
    private String name;

    /**
     * Конструктор экземпляра класса Location без аргументов
     *
     * @see Location#Location(Integer x, long y, float z, String name)
     */
    public Location() {
    }

    /**
     * Конструктор экземпляра класса Location, получающий аргументы x, y, z, name
     *
     * @param x    - координата x
     * @param y    - координата y
     * @param z    - координата z
     * @param name - поле name
     * @see Location#Location()
     */
    public Location(Integer x, long y, float z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    /**
     * Метод получения координаты x
     *
     * @return x
     */
    public Integer getX() {
        return this.x;
    }

    /**
     * Метод получения координаты y
     *
     * @return y
     */
    public long getY() {
        return this.y;
    }

    /**
     * Метод получения координаты z
     *
     * @return z
     */
    public float getZ() {
        return this.z;
    }

    /**
     * Метод получения поля name
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Метод установки значения координаты x
     *
     * @param x - координата x
     */
    public void setX(Integer x) {
        this.x = x;
    }

    /**
     * Метод установки значения координаты y
     *
     * @param y - координата y
     */
    public void setY(long y) {
        this.y = y;
    }

    /**
     * Метод установки значения координаты z
     *
     * @param z - координата z
     */
    public void setZ(float z) {
        this.z = z;
    }

    /**
     * Метод установки значения поля name
     *
     * @param name - поле x
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Метод проверки координаты x
     *
     * @param x - координата x
     * @return x
     * @throws NumberFormatException если x не прошла проверку
     */

    public static Integer checkX(Integer x) {
        if (x != null && !x.equals("")) {
            return x;
        } else {
            throw new NumberFormatException("Invalid parameter x value");
        }
    }

    /**
     * Метод проверки поля name
     *
     * @param name - поле name
     * @return name
     * @throws NumberFormatException если name не прошло проверку
     */
    public static String checkName(String name) {
        if (name != null && !name.equals("")) {
            return name;
        } else {
            throw new NumberFormatException("Invalid parameter name value");
        }
    }
}


