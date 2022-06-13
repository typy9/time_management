package com.project.time_management.dao;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.project.time_management.entity.Activity;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class ActivityDAOTest {

    MysqlConnectionPoolDataSource dataSource;
    ActivityDAO activityDAO;

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
        activityDAO = new ActivityDAO(ConnectionPool.getConnection());
    }

    @Test
    public void findAllTest() throws DBException {
        assertNotNull(activityDAO.findAll());
    }

    @Test
    public void findEntityByIdTest() throws DBException {
        assertNotNull(activityDAO.findEntityById(1));
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
    public void findActivitiesByCategory() {
    }

    @Test
    public void findActivityIdByName() {
    }

    @Test
    public void getRecords() {
    }

    @Test
    public void findAllWithLimits() {
    }
}