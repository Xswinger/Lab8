package ru.itmo.server.utils;

import java.io.InputStream;

public class FileUtil {

    public static InputStream filePath(String fileName) {
        ClassLoader classLoader = FileUtil.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }
}
