package com.project.time_management.services.user;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateUserServiceTest {

    private static final Logger LOG = Logger.getLogger(UpdateUserServiceTest.class);
    MysqlConnectionPoolDataSource dataSource;
    UpdateUserService service;

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
        service = new UpdateUserService(dataSource);
    }

    @Test
    void getUserForUpdateTest() {
        LOG.debug("getUserForUpdateTest()");
        assertNull(service.getUserForUpdate(""));
        assertNull(service.getUserForUpdate("no"));
        assertNotNull(service.getUserForUpdate("1"));
        assertEquals("ivanov", service.getUserForUpdate("1").getName());
    }

    @Test
    void updateUserTest() {
        LOG.debug("updateUserTest()");
        assertNull(service.updateUser("one", "test", "user"));
        assertEquals("test", service.updateUser("1", "test", "user").get(0).getName());
        service.updateUser("1", "ivanov", "admin");
    }
}