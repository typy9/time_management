package com.project.time_management.services.category;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateCategoryServiceTest {

    private static final Logger LOG = Logger.getLogger(UpdateCategoryServiceTest.class);
    MysqlConnectionPoolDataSource dataSource;
    UpdateCategoryService service;

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
        service = new UpdateCategoryService(dataSource);
    }

    @Test
    void getCategoryForUpdateTest() {
        LOG.debug("getCategoryForUpdateTest()");
        assertNull(service.getCategoryForUpdate("hu"));
        assertNotNull(service.getCategoryForUpdate("1"));
        assertEquals("general", service.getCategoryForUpdate("1").getName());
    }

    @Test
    void updateCategoryTest() {
        LOG.debug("updateActivityTest()");
        assertNotEquals(1, service.updateCategory(0, "test").size());
        assertEquals("test", service.updateCategory(1, "test").get(0).getName());
        service.updateCategory(1, "general");
    }
}