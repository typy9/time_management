package com.project.time_management.servlets.category;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.apache.log4j.Logger;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class DisplayCategoriesServletTest {

    private static final Logger LOG = Logger.getLogger(DisplayCategoriesServletTest.class);

    @Mock
    HttpServletRequest mockRequest;

    @Mock
    HttpServletResponse mockResponse;

    @Mock
    HttpSession mockSession;

    @Mock
    RequestDispatcher mockRequestDispatcher;

    @InjectMocks
    DisplayCategoriesServlet mockServlet;

    MysqlConnectionPoolDataSource dataSource;


    @BeforeEach
    public void setUp() throws IOException {

        MockitoAnnotations.initMocks(this);
        mockServlet = new DisplayCategoriesServlet();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dataSource = new MysqlConnectionPoolDataSource();
            dataSource.setURL("jdbc:mysql://127.0.0.1:3306/test_db");
            dataSource.setUser("root");
            dataSource.setPassword("Bajeu4fg5$");

        } catch(ClassNotFoundException ex) {
            LOG.error("Cannot get dataSource");
        }

    }

    @Test
    void doGetTest() throws ServletException, IOException {
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockRequest.getRequestDispatcher("/categories_list.jsp")).thenReturn(mockRequestDispatcher);

        mockServlet.setDataSource(dataSource);
        mockServlet.doGet(mockRequest, mockResponse);

        verify(mockSession).setAttribute("view", "/categories_list");
        verify(mockRequest, times(1)).getRequestDispatcher("/categories_list.jsp");
        verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }
}