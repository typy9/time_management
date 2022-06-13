package com.project.time_management.utility;

import static org.junit.Assert.*;
import org.junit.Test;

public class UtilityTest {

    @Test
    public void utilityNameTest() {
        assertTrue(Utility.validateName("h"));
        assertTrue(Utility.validateName("77"));
        assertTrue(Utility.validateName("Smith99"));

        assertFalse(Utility.validateName(""));
        assertFalse(Utility.validateName("FoobarFoobarFoobar"));
        assertFalse(Utility.validateName("smith99-"));
    }

    @Test
    public void utilityLoginTest() {
        assertTrue(Utility.validateLogin("a"));
        assertTrue(Utility.validateLogin("My_Login"));
        assertTrue(Utility.validateLogin(".log"));

        assertFalse(Utility.validateLogin(""));
        assertFalse(Utility.validateLogin(".Foo_Foo.Foobar_"));
        assertFalse(Utility.validateLogin("&Foobar$"));
        assertFalse(Utility.validateLogin("Blake90"));
    }

    @Test
    public void utilityPasswordTest() {
        assertTrue(Utility.validatePassword("h%"));
        assertTrue(Utility.validatePassword("77"));
        assertTrue(Utility.validatePassword("dg&_3kBp"));

        assertFalse(Utility.validatePassword("*"));
        assertFalse(Utility.validatePassword("+p"));
        assertFalse(Utility.validatePassword("myPass1234myPass"));
    }

    @Test
    public void utilityEmailTest() {
        assertTrue(Utility.validateEmail("a_b@gmail.com"));
        assertTrue(Utility.validateEmail("a.c.block@yahoo.com"));
        assertTrue(Utility.validateEmail("lambert@stanford.edu"));

        assertFalse(Utility.validateEmail("a@gmail.com"));
        assertFalse(Utility.validateEmail("s_kelly.gmail.com"));
        assertFalse(Utility.validateEmail("abc_def@gmail"));
    }
}
