package com.project.time_management.entity;

import java.util.Objects;

public class User extends Entity {

    private int userId;
    private String name;
    private String role;

    public User() {
    }

    public User(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && name.equals(user.name) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public static User createUser(String name, Role role) {
        User user = new User();
        user.setName(name);
        user.setRole(String.valueOf(role));
        return user;
    }
    public static User createUser(String name) {
        User user = new User();
        user.setName(name);
        return user;
    }

    public enum ROLE {
        USER("user"), ADMIN("admin"), UNKNOWN("unknown"), NEW("new");
        private final String value;
        ROLE(String value) {this.value = value;}
        public String getValue() {
            return value;
        }
    }
}
