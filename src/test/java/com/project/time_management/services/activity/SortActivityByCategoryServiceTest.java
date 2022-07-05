package com.project.time_management.services.activity;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortActivityByCategoryServiceTest {

    private static final Logger LOG = Logger.getLogger(SortActivityByCategoryServiceTest.class);
    MysqlConnectionPoolDataSource dataSource;
    SortActivityByCategoryService service;

    @BeforeEach
    void setUp() {
        LOG.debug("Start setUp()");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dataSource = new MysqlConnectionPoolDataSource();
            dataSource.setURL("jdbc:mysql://127.0.0.1:3306/test_db");
            dataSource.setUser("root");
            dataSource.setPassword("Bajeu4fg5$");

        } catch(ClassNotFoundException ex) {
            LOG.trace("Cannot get dataSource");
        }
        service = new SortActivityByCategoryService(dataSource);
    }

    @Test
    void getSortedActivitiesTest() {
        LOG.debug("Start getSortedActivitiesTest()");
        assertEquals(3, service.getSortedActivities().size());
    }
}