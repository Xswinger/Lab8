package ru.itmo.server.database.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.server.database.DatabaseConnector;
import ru.itmo.server.utils.PasswordHasher;

import java.sql.*;
import java.time.LocalDate;
public class UsersDao extends AbstractDao {
    private static final Logger logger = LoggerFactory.getLogger(UsersDao.class);
    private static UsersDao instance = null;

    public static UsersDao getInstance() {
        if (instance == null) {
            instance = new UsersDao();
        }
        return instance;
    }
    public Integer addUser(String userName, String userPassword) throws SQLException {
        ResultSet resultSet = getDatabaseConnector().createStatement()
                .executeQuery("SELECT name FROM users");
        while (resultSet.next()){
            if (resultSet.getString("name").equals(userName)){
                logger.info("Attempt to register a user with an already existing login: {}", userName);
                return -1;
            }
        }
        int id = Math.toIntExact(generateId());
        PreparedStatement preparedStatement = getDatabaseConnector()
                .createPreparedStatement("INSERT INTO users (id, name, password, registration_date) VALUES (?, ?, ?, ?)");
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, userName);
        if (userPassword == null) {
            preparedStatement.setString(3, null);
        } else {
            preparedStatement.setString(3, PasswordHasher.encryptPassword(userPassword));
        }
        preparedStatement.setObject(4, LocalDate.now());
        preparedStatement.executeUpdate();
        logger.info("User with login {} add successfully", userName);
        return id;
    }
    public Integer checkUser(String userName, String userPassword) throws SQLException {
        PreparedStatement preparedStatement;
        if (userPassword == null) {
            preparedStatement = getDatabaseConnector().createPreparedStatement(
                    "SELECT id FROM users WHERE name = ? AND password IS NULL");
        } else {
            preparedStatement = DatabaseConnector.getInstance().createPreparedStatement(
                "SELECT id FROM users WHERE name = ? AND password = ?");
            preparedStatement.setString(2, PasswordHasher.encryptPassword(userPassword));
        }
        preparedStatement.setString(1, userName);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            logger.info("User with login {} found", userName);
            return resultSet.getInt("id");
        } else {
            logger.info("User with login {} not found", userName);
            return -1;
        }
    }

    public Date getRegistrationDate(int userId) throws SQLException {
        PreparedStatement preparedStatement = getDatabaseConnector().createPreparedStatement(
                "SELECT registration_date FROM users WHERE id = ?");
        preparedStatement.setInt(1, userId);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        resultSet.next();
        return resultSet.getDate("registration_date");
    }
    @Override
    protected String getSeqName() {
        return "users_id_seq";
    }
}