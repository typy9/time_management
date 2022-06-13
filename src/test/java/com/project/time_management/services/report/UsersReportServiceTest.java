package com.project.time_management.services.report;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class UsersReportServiceTest {

    MysqlConnectionPoolDataSource dataSource;
    UsersReportService service;
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
        service = new UsersReportService(dataSource);
    }

    @Test
    public void displayUserReportNotNullTest() {
        assertNotNull(service.displayUserReport(1, 2));
    }
}