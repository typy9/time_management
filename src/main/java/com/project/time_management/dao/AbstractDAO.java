package com.project.time_management.dao;

import com.project.time_management.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDAO <T extends Entity> {
    protected Connection connection;
    public AbstractDAO(Connection connection) {
        this.connection = connection;
    }

    private AbstractDAO() {

    }

    public abstract List<T> findAll() throws DBException;
    public abstract Optional<T> findEntityById(int id) throws DBException;
    public abstract boolean delete(int id) throws DBException;
    public abstract boolean delete(T... entity) throws DBException;
    public abstract boolean create(T entity) throws DBException;
    public abstract boolean update(T entity) throws DBException;

    public void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {

        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
