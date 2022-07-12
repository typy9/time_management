package com.project.time_management.entity;

public class UserCredentials extends Entity {
    private int id;
    private int userId;
    private String login;
    private String password;

    public UserCredentials() {
    }

    public UserCredentials(int userId, String login, String password) {
        this.id = id;
        this.userId = userId;
        this.login = login;
        this.password = password;
    }

    public UserCredentials(int id, int userId, String login, String password) {
        this.id = id;
        this.userId = userId;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Override
    public String toString() {
        return "\n UserCredentials{" +
                "id=" + id +
                ", userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
