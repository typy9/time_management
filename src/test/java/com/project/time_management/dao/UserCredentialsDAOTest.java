package com.project.time_management.dao;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserCredentialsDAOTest {

    MysqlConnectionPoolDataSource dataSource;
    UserCredentialsDAO userCredentialsDAO;
    @Before
    public void setUp() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dataSource = new MysqlConnectionPoolDataSource();
            dataSource.setURL("jdbc:mysql://127.0.0.1:3306/project_db");
            dataSource.setUser("root");
            dataSource.setPassword("Bajeu4fg5$");
            new ConnectionPool(dataSource);
        } catch(ClassNotFoundException ex) {
            System.out.println("Cannot get dataSource");
        }
        userCredentialsDAO = new UserCredentialsDAO(ConnectionPool.getConnection());
    }

    @Test
    public void findAllTest() throws DBException {
        assertNull(userCredentialsDAO.findAll());
    }

    @Test
    public void findEntityById() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void testDelete() {
    }

    @Test
    public void create() {
    }

    @Test
    public void update() {
    }

    @Test
    public void getUserByLoginPassword() {
    }

    @Test
    public void getRoleByLoginPassword() {
    }

    @Test
    public void userExists() {
    }

    @Test
    public void findEntityByName() {
    }
}