package com.project.time_management.helpers;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.project.time_management.dao.ConnectionPool;
import com.project.time_management.dao.UserCredentialsDAO;
import com.project.time_management.dao.UserDAO;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import javax.sql.DataSource;

import static org.junit.Assert.*;

public class UserCredentialsValidatorHelperTest {

    MysqlConnectionPoolDataSource dataSource;

    UserCredentialsValidatorHelper testClass;

    @Before
    public void setUp() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dataSource = new MysqlConnectionPoolDataSource();
            dataSource.setURL("jdbc:mysql://127.0.0.1:3306/project_db");
            dataSource.setUser("root");
            dataSource.setPassword("Bajeu4fg5$");
        } catch(ClassNotFoundException ex) {
            System.out.println("Cannot get dataSource");
        }
        testClass = new UserCredentialsValidatorHelper(dataSource);
    }

    @Test
    public void loginExistsTest() {
        assertTrue(testClass.loginExists("a"));
        assertTrue(testClass.loginExists("u"));
    }

    @Test
    public void loginNotExistsTest() {
        assertFalse(testClass.loginExists("c"));
        assertFalse(testClass.loginExists(""));
        assertFalse(testClass.loginExists("23"));
    }

    @Test
    public void nameExistsTest() {
        assertTrue(testClass.nameExists("ivanov"));
        assertTrue(testClass.nameExists("petrov"));
    }

    @Test
    public void nameNotExistsTest() {
        assertFalse(testClass.nameExists("levin"));
        assertFalse(testClass.nameExists("fooBar"));
        assertFalse(testClass.nameExists(""));
    }
}