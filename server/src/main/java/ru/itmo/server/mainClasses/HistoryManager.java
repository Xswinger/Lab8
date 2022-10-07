package ru.itmo.server.mainClasses;

import java.util.*;

/**
 * Класс History
 */
public class HistoryManager {
    /**
     * Список для хранения выполненных команд
     */
    private static HistoryManager instance = null;

    public static HistoryManager getInstance() {
        if (instance == null) {
            instance = new HistoryManager();
        }
        return instance;
    }
    private final Map<Integer, String> history = new TreeMap<>();
    public void addHistory(int userId, String command) {
        if (history.containsKey(userId)) {
            String oldHistory = history.get(userId);
            history.put(userId, oldHistory + "\n" + command);
        } else {
            history.put(userId, command);
        }
    }

    public String historyOfUser(int userId) {
        return history.get(userId);
    }

    public void clearHistory(int userId) {
        history.remove(userId);
    }
}
