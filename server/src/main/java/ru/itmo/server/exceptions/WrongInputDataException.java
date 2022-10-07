package ru.itmo.server.exceptions;

/**
 * Класс исключения, если будут введены неверные данные
 */
public class WrongInputDataException extends Exception {
    /**
     * Конструктор WrongInputDataException, принимающий message
     *
     * @param message - сообщение исключения
     */
    public WrongInputDataException(String message) {
        super(message);
    }
}
