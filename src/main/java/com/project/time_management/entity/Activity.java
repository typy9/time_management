package com.project.time_management.entity;

import java.util.Objects;

public class Activity extends Entity {
    private int activityId;
    private String name;
    private int category;

    public Activity() {
    }

    public Activity(String name, int category) {
        this.name = name;
        this.category = category;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return activityId == activity.activityId && category == activity.category && Objects.equals(name, activity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityId, name, category);
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", name='" + name + '\'' +
                ", category=" + category +
                '}';
    }

    public static Activity createActivity(String name) {
        Activity activity = new Activity();
        activity.setName(name);
        return activity;
    }
}
