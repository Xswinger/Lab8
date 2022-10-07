package ru.itmo.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager {
    private static ExecutorService instance = null;

    public static ExecutorService getExecutor() {
        if (instance == null) {
            instance = Executors.newCachedThreadPool();
        }
        return instance;
    }
}
