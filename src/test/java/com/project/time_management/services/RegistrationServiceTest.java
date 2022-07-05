package com.project.time_management.services;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegistrationServiceTest {

    MysqlConnectionPoolDataSource dataSource;
    RegistrationService registrationService;

    @Before
    public void setUp() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dataSource = new MysqlConnectionPoolDataSource();
            dataSource.setURL("jdbc:mysql://127.0.0.1:3306/project_db");
            dataSource.setUser("root");
            dataSource.setPassword("Bajeu4fg5$");
        } catch(ClassNotFoundException ex) {
            System.out.println("Cannot get dataSource");
        }
        registrationService = new RegistrationService(dataSource);
    }

    @Test
    public void checkCredentials() {
    }

    @Test
    public void userRegistrationPermittedTest() {
        assertTrue(registrationService.userRegistrationPermitted("blue","aa","aw3"));
        assertTrue(registrationService.userRegistrationPermitted("red","b","_Tn4"));

        assertFalse(registrationService.userRegistrationPermitted("blue","a","aw3"));
        assertFalse(registrationService.userRegistrationPermitted("aa","user2","aw3"));
    }
}