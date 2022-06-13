package com.project.time_management.entity;

public class UsersActivityFull extends Entity {
    private int id;
    private String user_name;
    private String activity_name;
    private int time;

    public UsersActivityFull() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public static UsersActivityFull createUserActivity(String user_name, String activity_name) {
        UsersActivityFull record = new UsersActivityFull();
        record.setUser_name(user_name);
        record.setActivity_name(activity_name);
        return record;
    }
}
