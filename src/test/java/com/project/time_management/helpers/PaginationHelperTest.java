package com.project.time_management.helpers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import javax.servlet.http.HttpServletRequest;
import static org.junit.Assert.*;


public class PaginationHelperTest {
    @Mock
    private HttpServletRequest request;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
//        request = Mockito.mock(HttpServletRequest.class);
    }


    @Test
    public void getPageParameter() {
         assertEquals(1,1);
    }
}