package ru.itmo.server.database.dao;

import ru.itmo.server.database.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDao {
    protected abstract String getSeqName();
    protected DatabaseConnector getDatabaseConnector() {
        return DatabaseConnector.getInstance();
    }
    public Long generateId() throws SQLException{
        String sql = "SELECT nextval('" + getSeqName() + "')";
        ResultSet resultSet = getDatabaseConnector().createStatement()
                .executeQuery(sql);
        resultSet.next();
        return resultSet.getLong(1);
    }
}
