package com.project.time_management.helpers;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class AuthenticationHelperTest {

    AuthenticationHelper authenticationHelper;
    private static final List<String> outOfControl = new ArrayList<>();
    private static final List<String> commons = new ArrayList<>();
    private static final Map<String, List<String>> accessMap = new HashMap<>();


    @Before
    public void setUp() {

        outOfControl.addAll(Stream.of("utils.Login", "utils.Registration", "utils.DisplayRegistration",
                "utils.SetLocaleEn", "utils.SetLocaleUkr").collect(Collectors.toList()));
        commons.add("activity.UpdateActivityTime");
        accessMap.put("admin", Stream.of("user.DisplayUser", "activity.DisplayActivity", "category.DisplayCategory",
                        "report.DisplayReport", "request.DisplayRequest", "user.UpdateUser", "user.DeleteUser",
                        "user.UpdateUser", "user.AddUser", "request.ApproveRequest", "request.DeclineRequest",
                        "category.DeleteCategory", "category.UpdateCategory", "category.AddCategory",
                        "category.UpdateCategory", "activity.FilterActivityByCategory", "activity.DeleteActivity",
                        "activity.UpdateActivity", "activity.UpdateActivityTime", "activity.AddActivity",
                        "utils.DispatchToAdminMain")
                .collect(Collectors.toList()));
        accessMap.put("user", Stream.of("activity.UpdateActivityTime", "request.AddRequest")
                .collect(Collectors.toList()));

        authenticationHelper = new AuthenticationHelper();
    }

    @Test
    public void accessAllowedOutOfControl() {

        assertTrue(authenticationHelper.accessAllowed("utils.Login", "admin",
                outOfControl, commons, accessMap));
        assertTrue(authenticationHelper.accessAllowed("utils.DisplayRegistration", "user",
                outOfControl, commons, accessMap));

    }

    @Test
    public void accessAllowedCommons() {

        assertTrue(authenticationHelper.accessAllowed("activity.UpdateActivityTime", "user",
                outOfControl, commons, accessMap));
        assertTrue(authenticationHelper.accessAllowed("activity.UpdateActivityTime", "admin",
                outOfControl, commons, accessMap));
    }

    @Test
    public void accessAllowedAdmin() {

        assertFalse(authenticationHelper.accessAllowed("utils.DispatchToAdminMain", "user",
                outOfControl, commons, accessMap));
        assertTrue(authenticationHelper.accessAllowed("utils.DispatchToAdminMain", "admin",
                outOfControl, commons, accessMap));
        assertFalse(authenticationHelper.accessAllowed("request.ApproveRequest", "user",
                outOfControl, commons, accessMap));
        assertFalse(authenticationHelper.accessAllowed("user.DeleteUser", "user",
                outOfControl, commons, accessMap));
    }

    @Test
    public void accessAllowedUser() {

        assertTrue(authenticationHelper.accessAllowed("request.AddRequest", "user",
                outOfControl, commons, accessMap));
        assertFalse(authenticationHelper.accessAllowed("request.AddRequest", "admin",
                outOfControl, commons, accessMap));

    }
}