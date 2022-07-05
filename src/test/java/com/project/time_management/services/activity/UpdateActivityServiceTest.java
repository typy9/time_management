package com.project.time_management.services.activity;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateActivityServiceTest {

    private static final Logger LOG = Logger.getLogger(UpdateActivityServiceTest.class);
    MysqlConnectionPoolDataSource dataSource;
    UpdateActivityService service;

    @BeforeEach
    public void setUp() {
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
        service = new UpdateActivityService(dataSource);
    }

    @Test
    void getActivityForUpdateTest() {
        LOG.debug("getActivityForUpdateTest()");
        assertNull(service.getActivityForUpdate("hu"));
        assertNotNull(service.getActivityForUpdate("1"));
        assertEquals("project management", service.getActivityForUpdate("1").getName());
    }

    @Test
    void updateActivityTest() {
        LOG.debug("updateActivityTest()");
        assertNull(service.updateActivity("one", "test", 1));
        assertEquals("test", service.updateActivity("1", "test", 2).get(0).getName());
        service.updateActivity("1", "project management", 1);
    }
}