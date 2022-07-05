package com.project.time_management.services.request;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeclineRequestsServiceTest {

    private static final Logger LOG = Logger.getLogger(DeclineRequestsServiceTest.class);
    MysqlConnectionPoolDataSource dataSource;
    DeclineRequestsService service;

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
        service = new DeclineRequestsService(dataSource);
    }

    @Test
    void declineRequestTest() {
        LOG.debug("declineRequestTest()");
        assertNull(service.declineRequest("number"));
        assertNull(service.declineRequest(""));
    }
}