package com.project.time_management.services.activity;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DisplayActivitiesServiceTest {

    MysqlConnectionPoolDataSource dataSource;
    DisplayActivitiesService service;

    @Before
    public void setUp() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dataSource = new MysqlConnectionPoolDataSource();
            dataSource.setURL("jdbc:mysql://127.0.0.1:3306/test_db");
            dataSource.setUser("root");
            dataSource.setPassword("Bajeu4fg5$");
        } catch(ClassNotFoundException ex) {
            System.out.println("Cannot get dataSource");
        }
        service = new DisplayActivitiesService(dataSource);
    }

    @Test
    public void getActivitiesNotNullTest() {
        assertNotNull(service.getActivities(1, 2));
    }
}