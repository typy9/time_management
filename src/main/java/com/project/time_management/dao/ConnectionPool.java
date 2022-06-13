package com.project.time_management.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;

import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool {

    private static final Logger LOG = Logger.getLogger(ConnectionPool.class);
    private static DataSource dataSource;

    /**
     * Constructor for JUnit tests.
     */
    public ConnectionPool(DataSource dataSource) {
        ConnectionPool.dataSource = dataSource;
    }

    /**
     * Get free connection from a pool.
     * @return connection.
     */
    public static synchronized Connection getConnection() {
        LOG.debug("Start getConnection");
        if (dataSource == null) {
            try {
                Context initContext = new InitialContext();
                Context envContext = (Context) initContext.lookup("java:/comp/env");
                dataSource = (DataSource) envContext.lookup("jdbc/project");
            } catch (NamingException e) {
                LOG.error("Cannot find the data source");
            }
        }

        try {
            LOG.debug("Finish getConnection");
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOG.error("Cannot establish connection");
            return null;
        }
    }

}
