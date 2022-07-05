package com.project.time_management.dao;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class ActivityDAOTest {

    MysqlConnectionPoolDataSource dataSource;
    ActivityDAO activityDAO;

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
        activityDAO = new ActivityDAO(ConnectionPool.getConnection());
    }

    @Test
    public void findAllTest() throws DBException {
        assertNotNull(activityDAO.findAll());
        assertEquals(3, activityDAO.findAll().size());
    }

    @Test
    public void findEntityByIdTest() throws DBException {
        assertNotNull(activityDAO.findEntityById(1));
        assertFalse(activityDAO.findEntityById(100).isPresent());

        assertTrue(activityDAO.findEntityById(3).isPresent());
        assertEquals("admin", activityDAO.findEntityById(3).get().getName());
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

//    @Test
//    public void update() {
//    }

    @Test
    public void findActivitiesByCategoryTest() throws DBException {
        assertTrue(activityDAO.findActivitiesByCategory(0).isEmpty());
        assertFalse(activityDAO.findActivitiesByCategory(1).isEmpty());
    }

    @Test
    public void findActivityIdByNameTest() throws DBException {
        assertEquals(1, activityDAO.findActivityIdByName("project management"));
        assertEquals(0, activityDAO.findActivityIdByName("test activity"));
    }

    @Test
    public void getRecordsTest() throws DBException {
        assertNotNull(activityDAO.getRecords(1, 2));
    }

    @Test
    public void findAllWithLimitsTest() throws DBException {
        assertNotNull(activityDAO.findAllWithLimits(1, 2));
    }
}