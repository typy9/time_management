package com.project.time_management.services.request;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApproveRequestsServiceTest {

    private static final Logger LOG = Logger.getLogger(ApproveRequestsServiceTest.class);
    MysqlConnectionPoolDataSource dataSource;
    ApproveRequestsService service;

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
        service = new ApproveRequestsService(dataSource);
    }

    @Test
    void getRequestsTest() {
        LOG.debug("getRequestsTest()");
        assertNull(service.getRequests(0));
        assertEquals(0, service.getRequests(1).size());
    }
}