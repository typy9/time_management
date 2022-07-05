package com.project.time_management.servlets.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

class LogoutServletTest {

    @Mock
    HttpServletRequest mockRequest;

    @Mock
    HttpServletResponse mockResponse;

    @Mock
    HttpSession mockSession;

    @Mock
    ServletContext mockServletContext;


    @Mock
    ServletConfig mockServletConfig;

    @InjectMocks
    LogoutServlet mockServlet;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockServlet = new LogoutServlet();
    }

    @Test
    void doGetTest() throws IOException, ServletException {

        mockServlet.init(mockServletConfig);
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockServlet.getServletContext()).thenReturn(mockServletContext);
        when(mockServletContext.getContextPath()).thenReturn("/logout");
        verify(mockResponse, times(0)).sendRedirect("/logout");
    }
}