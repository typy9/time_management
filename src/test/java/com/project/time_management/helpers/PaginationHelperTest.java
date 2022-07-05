package com.project.time_management.helpers;

import com.project.time_management.utility.MockedRequestWrapper;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.junit.Assert.*;


public class PaginationHelperTest {

    private MockedRequestWrapper requestWrapper;
    private PaginationHelper testClass;


    @Before
    public void setUp() throws ServletException, IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        requestWrapper = new MockedRequestWrapper(request);
        testClass = new PaginationHelper();
    }


    @Test
    public void getPageParameterTest1() {

        requestWrapper.setAttribute("page", 2);
        assertEquals(testClass.getPageParameter(requestWrapper), 2);
    }

    @Test
    public void getPageParameterTest2() {

        requestWrapper.setAttribute("page", 1);
        assertEquals(testClass.getPageParameter(requestWrapper), 1);
    }

    @Test
    public void getPageParameterTest3() {

        requestWrapper.setAttribute("page", null);
        assertEquals(testClass.getPageParameter(requestWrapper), 1);
    }

    @Test
    public void getPageParameterTest4() {

        requestWrapper.setAttribute("page", 0);
        assertEquals(testClass.getPageParameter(requestWrapper), 1);
    }
}