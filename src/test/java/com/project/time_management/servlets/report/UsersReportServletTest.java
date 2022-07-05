package com.project.time_management.servlets.report;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class UsersReportServletTest {

    @Mock
    HttpServletRequest mockRequest;

    @Mock
    HttpServletResponse mockResponse;

    @Mock
    HttpSession mockSession;

    @Mock
    RequestDispatcher mockRequestDispatcher;

    @InjectMocks
    UsersReportServlet mockServlet;

    MysqlConnectionPoolDataSource dataSource;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockServlet = new UsersReportServlet();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dataSource = new MysqlConnectionPoolDataSource();
            dataSource.setURL("jdbc:mysql://127.0.0.1:3306/test_db");
            dataSource.setUser("root");
            dataSource.setPassword("Bajeu4fg5$");
        } catch(ClassNotFoundException ex) {
            System.out.println("Cannot get dataSource");
        }
    }

    @Test
    void doGetTest() throws ServletException, IOException {
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockRequest.getRequestDispatcher("/users_activities.jsp")).thenReturn(mockRequestDispatcher);

        mockServlet.setDataSource(dataSource);
        mockServlet.doGet(mockRequest, mockResponse);

        verify(mockSession).setAttribute("view", "/users_report");
        verify(mockRequest, times(1)).getRequestDispatcher("/users_activities.jsp");
        verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }
}