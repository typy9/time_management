package com.project.time_management.servlets.utils;

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

class UserCabinetDispatcherServletTest {

    @Mock
    HttpServletRequest mockRequest;

    @Mock
    HttpServletResponse mockResponse;

    @Mock
    HttpSession mockSession;

    @Mock
    RequestDispatcher mockRequestDispatcher;

    @InjectMocks
    UserCabinetDispatcherServlet mockServlet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockServlet = new UserCabinetDispatcherServlet();
    }

    @Test
    void doGetTest() throws ServletException, IOException {

        when(mockRequest.getSession(false)).thenReturn(mockSession);

        when(mockRequest.getRequestDispatcher("/user_menu.jsp")).thenReturn(mockRequestDispatcher);
        mockServlet.doGet(mockRequest, mockResponse);

        verify(mockSession).setAttribute("view", "/user_cabinet");
        verify(mockRequest, times(1)).getRequestDispatcher("/user_menu.jsp");
        verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }
}