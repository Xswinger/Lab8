package ru.itmo.client;

import java.util.Date;

public class User {
    private static boolean creatingNewUser = false;
    private static boolean registrationStatus = false;
    private static Date registrationDate;
    private static long id;
    private String name;
    private String password;

    public User() {

    }
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public static Date getRegistrationDate() {
        return registrationDate;
    }

    public static void setRegistrationDate(Date registrationDate) {
        User.registrationDate = registrationDate;
    }

    public static long getId() {
        return id;
    }

    public static void setId(long id) {
        User.id = id;
    }

    public String getName() {
        return name;
    }

    public boolean isCreateNewUser() {
        return creatingNewUser;
    }

    public void setCreatingNewUser(boolean creatingNewUser) {
        User.creatingNewUser = creatingNewUser;
    }

    public String getPassword() {
        return password;
    }

    public boolean isRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(boolean registrationStatus) {
        User.registrationStatus = registrationStatus;
    }
}
