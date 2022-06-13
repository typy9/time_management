package com.project.time_management.services.request;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DisplayRequestsServiceTest {
    MysqlConnectionPoolDataSource dataSource;
    DisplayRequestsService service;
    @Before
    public void setUp() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dataSource = new MysqlConnectionPoolDataSource();
            dataSource.setURL("jdbc:mysql://127.0.0.1:3306/project_db");
            dataSource.setUser("root");
            dataSource.setPassword("Bajeu4fg5$");
        } catch(ClassNotFoundException ex) {
            System.out.println("Cannot get dataSource");
        }
        service = new DisplayRequestsService(dataSource);
    }

    @Test
    public void displayRequests() {
        assertNotNull(service.displayRequests());
    }
}