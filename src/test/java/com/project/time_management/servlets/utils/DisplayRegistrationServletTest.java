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

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class DisplayRegistrationServletTest {

    @Mock
    HttpServletRequest mockRequest;

    @Mock
    HttpServletResponse mockResponse;

    @Mock
    HttpSession mockSession;

    @Mock
    RequestDispatcher mockRequestDispatcher;

    @InjectMocks
    DisplayRegistrationServlet mockServlet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockServlet = new DisplayRegistrationServlet();
    }

    @Test
    void doGetTest() throws ServletException, IOException {

        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockRequest.getRequestDispatcher("/registration.jsp")).thenReturn(mockRequestDispatcher);

        mockServlet.doGet(mockRequest, mockResponse);

        verify(mockSession).setAttribute("view", "/display_registration");
        verify(mockRequest, times(1)).getRequestDispatcher("/registration.jsp");
        verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }
}