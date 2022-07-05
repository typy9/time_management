package com.project.time_management.servlets.utils;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.project.time_management.dao.ConnectionPool;
import com.project.time_management.dao.DBException;
import com.project.time_management.dao.UserDAO;
import com.project.time_management.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

class RegistrationServletTest {

    @Mock
    HttpServletRequest mockRequest;

    @Mock
    HttpServletResponse mockResponse;

    @Mock
    HttpSession mockSession;

    @Mock
    RequestDispatcher mockRequestDispatcher;

    @InjectMocks
    RegistrationServlet mockServlet;


    MysqlConnectionPoolDataSource dataSource;
    UserDAO userDAO;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockServlet = new RegistrationServlet();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dataSource = new MysqlConnectionPoolDataSource();
            dataSource.setURL("jdbc:mysql://127.0.0.1:3306/test_db");
            dataSource.setUser("root");
            dataSource.setPassword("Bajeu4fg5$");
            new ConnectionPool(dataSource);
            userDAO = new UserDAO(ConnectionPool.getConnection());
        } catch(ClassNotFoundException ex) {
            System.out.println("Cannot get dataSource");
        }
    }

    @AfterEach
    public void clean() throws DBException {
        User userToDelete = new User("test", "user");
        int id = userDAO.findId(userToDelete);
        userDAO.delete(id);
    }

    @Test
    void doPostTest() throws ServletException, IOException {


        mockSession.setAttribute("role", "user");
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockRequest.getRequestDispatcher("/index.jsp")).thenReturn(mockRequestDispatcher);

        when(mockRequest.getParameter("user_name")).thenReturn("test");
        when(mockRequest.getParameter("login")).thenReturn("t");
        when(mockRequest.getParameter("password")).thenReturn("7u");

        mockServlet.setDataSource(dataSource);
        mockServlet.doPost(mockRequest, mockResponse);

        verify(mockRequest, times(1)).getRequestDispatcher("/index.jsp");
        verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }
}