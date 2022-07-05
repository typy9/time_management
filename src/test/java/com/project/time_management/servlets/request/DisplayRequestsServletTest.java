package com.project.time_management.servlets.request;

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

class DisplayRequestsServletTest {

    @Mock
    HttpServletRequest mockRequest;

    @Mock
    HttpServletResponse mockResponse;

    @Mock
    HttpSession mockSession;

    @Mock
    RequestDispatcher mockRequestDispatcher;

    @InjectMocks
    DisplayRequestsServlet mockServlet;

    MysqlConnectionPoolDataSource dataSource;


    @BeforeEach
    public void setUp() throws IOException {

        MockitoAnnotations.initMocks(this);
        mockServlet = new DisplayRequestsServlet();

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
        when(mockRequest.getRequestDispatcher("/requests_list.jsp")).thenReturn(mockRequestDispatcher);

        mockServlet.setDataSource(dataSource);
        mockServlet.doGet(mockRequest, mockResponse);

        verify(mockSession).setAttribute("view", "/requests");
                verify(mockRequest, times(1)).getRequestDispatcher("/requests_list.jsp");
        verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }
}