package ru.itmo.common.dto;

import java.io.Serializable;

/**
 * Класс Coordinates
 */
public class Coordinates implements Serializable {
    /**
     * Координата x
     * Максимальное значение поля: 922
     */
    private long x;
    /**
     * Координата y
     * Значение поля должно быть больше -607, Поле не может быть null
     */
    private Integer y;

    /**
     * Конструктор Coordinates без аргументов
     *
     * @see Coordinates#Coordinates(long, Integer)
     */
    public Coordinates() {
    }

    /**
     * Конcтруктор Coordinates, получающий аргументы x, y
     *
     * @param x - координата x
     * @param y - координата y
     * @see Coordinates#Coordinates()
     */
    public Coordinates(long x, Integer y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Метод для получения поля x
     *
     * @return x
     */

    public long getX() {
        return x;
    }

    /**
     * Метод для получения поля y
     *
     * @return y
     */

    public Integer getY() {
        return y;
    }

    /**
     * Метод для установки поля x
     *
     * @param x - координата x
     */

    public void setX(long x) {
        this.x = x;
    }

    /**
     * Метод для установки поля y
     *
     * @param y - координата y
     */

    public void setY(Integer y) {
        this.y = y;
    }

    /**
     * Метод проверки координаты x
     *
     * @param x - координата x
     * @return x
     * @throws NumberFormatException если x не прошла проверку
     */
    public static long checkX(long x) {
        if (x <= 922) {
            return x;
        } else {
            throw new NumberFormatException("Invalid parameter x value");
        }
    }

    /**
     * Метод проверки координаты y
     *
     * @param y - координата y
     * @return y
     * @throws NumberFormatException если y не прошла проверку
     */
    public static Integer checkY(Integer y) {
        if (y != null && y > -607) {
            return y;
        } else {
            throw new NumberFormatException("Invalid parameter y value");
        }
    }
}