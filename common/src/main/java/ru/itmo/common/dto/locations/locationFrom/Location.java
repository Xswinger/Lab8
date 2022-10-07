package ru.itmo.common.dto.locations.locationFrom;

import java.io.Serializable;

/**
 * Класс Location
 */
public class Location implements Serializable {
    /**
     * Координата x
     */
    private float x;
    /**
     * Координата y
     */
    private double y;
    /**
     * Координата z
     * Поле не может быть null
     */
    private Integer z;

    /**
     * Конструктор экземпляра класса Location без аргументов
     *
     * @see Location#Location(float x, double y, Integer z)
     */
    public Location() {
    }

    /**
     * c
     *
     * @param x - координата x
     * @param y - координата y
     * @param z - координата z
     * @see Location#Location()
     */
    public Location(float x, double y, Integer z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Метод получения координаты x
     *
     * @return x
     */
    public float getX() {
        return x;
    }

    /**
     * Метод получения координаты y
     *
     * @return y
     */
    public double getY() {
        return y;
    }

    /**
     * Метод получения координаты z
     *
     * @return z
     */
    public Integer getZ() {
        return this.z;
    }

    /**
     * Метод установки значения координаты x
     *
     * @param x - координата x
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Метод установки значения координаты y
     *
     * @param y - координата y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Метод установки значения координаты z
     *
     * @param z - координата z
     */
    public void setZ(Integer z) {
        this.z = z;
    }

    /**
     * Метод проверки координаты z
     *
     * @param z - координата z
     * @return z
     * @throws NumberFormatException если z не прошла проверку
     */
    public static Integer checkZ(Integer z) {
        if (z != null) {
            return z;
        } else {
            throw new NumberFormatException("Invalid parameter z value");
        }
    }
}

