package com.project.time_management.dao;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.project.time_management.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDAOTest {

    MysqlConnectionPoolDataSource dataSource;
    UserDAO userDAO;

    @Before

    public void setUp() throws DBException {
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
        userDAO = new UserDAO(ConnectionPool.getConnection());
    }

//    @After
//    public void clean() throws DBException {
//        userDAO.delete(3);
//        userDAO.delete(4);
//    }

    @Test
    public void findAllTest() throws DBException {
        assertNotNull(userDAO.findAll());
        assertEquals(2, userDAO.findAll().size());
    }

    @Test
    public void findEntityByIdTest() throws DBException {
        assertNotNull(userDAO.findEntityById(1));
        assertFalse(userDAO.findEntityById(100).isPresent());

        assertTrue(userDAO.findEntityById(2).isPresent());
        assertEquals("petrov", userDAO.findEntityById(2).get().getName());
    }

//    @Test
//    public void createTest() throws DBException {
//        User user = new User("test", "user");
//
//        assertTrue(userDAO.create(user));
//
//        assertEquals(3, userDAO.findAll().size());
//        assertTrue(userDAO.delete(user.setUserId()));
//        assertEquals(2, userDAO.findAll().size());
//    }

//    @Test
//    public void deleteTest() throws DBException {
//        User user = new User("created", "user");
//        userDAO.create(user);
//
//        assertTrue(userDAO.delete(user.getUserId()));
//        assertEquals(3, userDAO.findAll().size());
//    }

    @Test
    public void update() throws DBException {
        User user = new User("test", "user");
        assertFalse(userDAO.update(null));
        assertTrue(userDAO.update(user));
        User userOld = new User("petrov", "user");
        assertTrue(userDAO.update(userOld));
    }

    @Test
    public void findIdTest() throws DBException {
        User user = new User("ivanov", "admin");
        assertEquals(1, userDAO.findId(user));
    }
}