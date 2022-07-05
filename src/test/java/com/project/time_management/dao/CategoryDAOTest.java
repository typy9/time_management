package com.project.time_management.dao;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.project.time_management.entity.Category;
import com.project.time_management.entity.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryDAOTest {

    MysqlConnectionPoolDataSource dataSource;
    CategoryDAO categoryDAO;

    @Before
    public void setUp() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dataSource = new MysqlConnectionPoolDataSource();
            dataSource.setURL("jdbc:mysql://127.0.0.1:3306/test_db");
            dataSource.setUser("root");
            dataSource.setPassword("Bajeu4fg5$");
            new ConnectionPool(dataSource);
        } catch(ClassNotFoundException ex) {
            System.out.println("Cannot get dataSource");
        }
        categoryDAO = new CategoryDAO(ConnectionPool.getConnection());
    }

    @Test
    public void findAllTest() throws DBException {
        assertNotNull(categoryDAO.findAll());
        assertEquals(3, categoryDAO.findAll().size());
    }

    @Test
    public void findEntityByIdTest() throws DBException {
        assertNotNull(categoryDAO.findEntityById(1));
        assertFalse(categoryDAO.findEntityById(100).isPresent());

        assertTrue(categoryDAO.findEntityById(2).isPresent());
        assertEquals("administration", categoryDAO.findEntityById(2).get().getName());
    }

//    @Test
//    public void delete() {
//    }
//
//    @Test
//    public void testDelete() {
//    }
//
//    @Test
//    public void createTest() {
//    }

    @Test
    public void updateTest() throws DBException {
        Category category = new Category(2, "test");
        assertFalse(categoryDAO.update(null));
        assertTrue(categoryDAO.update(category));
        Category categoryOld = new Category(2,"administration");
        assertTrue(categoryDAO.update(categoryOld));
    }
}