package com.project.time_management.entity;

import java.util.Objects;

public class UsersActivity extends Entity {
    private int id;
    private int user_id;
    private int activity_id;
    private int time;

    public UsersActivity() {
    }

    public UsersActivity(int id, int user_id, int activity_id, int time) {
        this.id = id;
        this.user_id = user_id;
        this.activity_id = activity_id;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersActivity that = (UsersActivity) o;
        return id == that.id && user_id == that.user_id && activity_id == that.activity_id && time == that.time;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, activity_id, time);
    }

    @Override
    public String toString() {
        return "UsersActivities{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", activity_id=" + activity_id +
                ", time=" + time +
                '}';
    }

    public static UsersActivity createUserActivity(int user_id, int activity_id) {
        UsersActivity record = new UsersActivity();
        record.setUser_id(user_id);
        record.setActivity_id(activity_id);
        return record;
    }
}
