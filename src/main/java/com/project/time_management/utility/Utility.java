package com.project.time_management.utility;

import java.util.regex.Pattern;

public class Utility {

    public static boolean validateName(String name) {
        Pattern pattern = Pattern.compile("[a-zA-Z\\d]{1,15}");
        return pattern.matcher(name).matches();
    }

    public static boolean validateLogin(String login) {
        Pattern pattern = Pattern.compile("[A-Za-z._]{1,15}");
        return pattern.matcher(login).matches();
    }

    public static boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile("[A-Za-z._@!#$%^&*()\\d]{2,15}");
        return pattern.matcher(password).matches();
    }

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("[A-Za-z\\d._]{2,25}@[A-Za-z\\d.]{2,15}\\.[A-Za-z]{2,6}");
        return pattern.matcher(email).matches();
    }
}
