package com.project.time_management.dao;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.project.time_management.entity.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserCredentialsDAOTest {

    MysqlConnectionPoolDataSource dataSource;
    UserCredentialsDAO userCredentialsDAO;

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
        userCredentialsDAO = new UserCredentialsDAO(ConnectionPool.getConnection());
    }

    @Test
    public void findAllTest() throws DBException {
        assertNull(userCredentialsDAO.findAll());

    }

    @Test
    public void findEntityByIdTest() throws DBException {
        assertNotNull(userCredentialsDAO.findEntityById(1));
        assertFalse(userCredentialsDAO.findEntityById(100).isPresent());
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
//    public void create() {
//    }
//
//    @Test
//    public void update() {
//    }

    @Test
    public void getUserByLoginPassword() throws DBException {

        assertNull(userCredentialsDAO.getUserByLoginPassword(null, null));
        assertNull(userCredentialsDAO.getUserByLoginPassword("u", null));
        assertNull(userCredentialsDAO.getUserByLoginPassword(null, "2"));

        assertNotNull(userCredentialsDAO.getUserByLoginPassword("u", "2"));
    }

    @Test
    public void getRoleByLoginPassword() throws DBException {

        assertEquals(User.ROLE.UNKNOWN, userCredentialsDAO.getRoleByLoginPassword(null, null));
        assertEquals(User.ROLE.UNKNOWN, userCredentialsDAO.getRoleByLoginPassword("u", null));
        assertEquals(User.ROLE.UNKNOWN, userCredentialsDAO.getRoleByLoginPassword(null, "2"));

        assertEquals(User.ROLE.ADMIN, userCredentialsDAO.getRoleByLoginPassword("a", "1"));
    }

    @Test
    public void userExists() throws DBException {
        assertFalse(userCredentialsDAO.userExists(null, "2"));
        assertFalse(userCredentialsDAO.userExists(null, null));

        assertTrue(userCredentialsDAO.userExists("u", "2"));
    }

    @Test
    public void findEntityByName() throws DBException {
        assertFalse(userCredentialsDAO.findEntityByName(null));
        assertTrue(userCredentialsDAO.findEntityByName("u"));
    }
}