package ru.itmo.common.dto;

import java.util.List;

public class Print {
    //Вывод ответа от сервера
    public static StringBuilder printContent(Message content) {
        StringBuilder message = new StringBuilder();
        if (content.getContentString() != null) {
            message.append(content.getContentString());
        }
        if (content.getContentRoute() != null) {
            message.append(routeToString(content.getContentRoute()));
        }
        message.append("\n");
        return message;
    }

    //Вывод в терминал объектов в читаемой форме
    public static StringBuilder routeToString(List<Route> routes) {
        StringBuilder print = new StringBuilder();
        for (Route route : routes) {
            if (route.getFrom() == null) {
                print.append(String.format("%-7s %-14s %-7s %-7s %-18s %-7s %-7s %-9s %-10s %-3s%n",
                        "Id: " + route.getId(), "Name: " + route.getName(), "X: " + route.getCoordinates().getX(), "Y: " +
                                route.getCoordinates().getY(), "Date: " + route.getCreationDate(),
                        "X: " + route.getTo().getX(), "Y: " + route.getTo().getY(), "Z: " + route.getTo().getZ(),
                        "Name: " + route.getTo().getName(), "Distance: " + route.getDistance()));
            } else {
                print.append(String.format("%-7s %-14s %-7s %-7s %-18s %-9s %-9s %-7s %-7s %-7s %-7s %-10s %-3s%n",
                        "Id: " + route.getId(), "Name: " + route.getName(), "X: " + route.getCoordinates().getX(), "Y: " +
                                route.getCoordinates().getY(), "Date: " + route.getCreationDate(),
                        "X: " + route.getFrom().getX(), "Y: " + route.getFrom().getY(), "Z: " + route.getFrom().getZ(),
                        "X: " + route.getTo().getX(), "Y: " + route.getTo().getY(), "Z: " +
                                route.getTo().getZ(), "Name: " + route.getTo().getName(), "Distance " + route.getDistance()));
            }
        }
        return print;
    }
}
