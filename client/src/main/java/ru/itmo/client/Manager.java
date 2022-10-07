package ru.itmo.client;

import ru.itmo.client.gui.MainFrame;

import javax.swing.*;
import java.io.IOException;

/**
 * Класс запуска работы программы
 */
public class Manager {

    /**
     * Класс Manager
     *
     * @param args - аргументы командной строки
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new MainFrame().MainWindow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
}