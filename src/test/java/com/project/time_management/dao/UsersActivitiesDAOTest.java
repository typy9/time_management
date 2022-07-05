package com.project.time_management.dao;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsersActivitiesDAOTest {

    MysqlConnectionPoolDataSource dataSource;
    UsersActivitiesDAO userActivitiesDAO;

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
        userActivitiesDAO = new UsersActivitiesDAO(ConnectionPool.getConnection());
    }

    @Test
    public void findAllTest() throws DBException {
        assertNotNull(userActivitiesDAO.findAll());
        assertEquals(4, userActivitiesDAO.findAll().size());
    }

    @Test
    public void findEntityById() throws DBException {
        assertNotNull(userActivitiesDAO.findEntityById(1));
        assertFalse(userActivitiesDAO.findEntityById(100).isPresent());
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
    public void findAllUserActivitiesByUserId() throws DBException {
        assertEquals(2, userActivitiesDAO.findAllUserActivitiesByUserId(1).size());
    }
}