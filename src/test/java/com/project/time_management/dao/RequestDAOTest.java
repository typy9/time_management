package com.project.time_management.dao;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RequestDAOTest {

    MysqlConnectionPoolDataSource dataSource;
    RequestDAO requestDAO;
    @Before
    public void setUp() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dataSource = new MysqlConnectionPoolDataSource();
            dataSource.setURL("jdbc:mysql://127.0.0.1:3306/test_db");
            dataSource.setUser("root");
            dataSource.setPassword("Bajeu4fg5$");
            new ConnectionPool(dataSource);
        } catch(ClassNotFoundException ex) {
            System.out.println("Cannot get dataSource");
        }
        requestDAO = new RequestDAO(ConnectionPool.getConnection());
    }

    @Test
    public void findAllTest() throws DBException {
        assertTrue(requestDAO.findAll().isEmpty());
    }

    @Test
    public void findEntityById() throws DBException {
        assertFalse(requestDAO.findEntityById(0).isPresent());
    }

//    @Test
//    public void delete() {
//    }
//
//    @Test
//    public void testDelete() {
//    }
//
//    @Test
//    public void create() {
//    }
//
//    @Test
//    public void update() {
//    }

    @Test
    public void updateStatusById() throws DBException {
        assertFalse(requestDAO.updateStatusById(0, "approved"));
    }

    @Test
    public void getStatusById() throws DBException {
        assertNull(requestDAO.getStatusById(0));
    }
}