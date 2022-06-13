package com.project.time_management.entity;

import java.util.Objects;

public class Request extends Entity {
    private int request_id;
    private int userId;
    private int activityId;

    private int time;
    private String status;

    public Request() {
    }

    public Request(int id, int userId, int activityId, String status) {
        this.request_id = id;
        this.userId = userId;
        this.activityId = activityId;
        this.status = status;
    }

    public Request(int userId, int activityId, String status) {
        this.userId = userId;
        this.activityId = activityId;
        this.status = status;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int activityId) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return request_id == request.request_id && userId == request.userId && activityId == request.activityId && Objects.equals(status, request.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(request_id, userId, activityId, status);
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + request_id +
                ", userId=" + userId +
                ", activityId=" + activityId +
                ", time=" + time +
                ", status='" + status + '\'' +
                '}';
    }

    public static Request createRequest(int request_id, RequestStatus status) {
        Request request = new Request();
        request.setRequest_id(request_id);
        request.setStatus(String.valueOf(status));
        return request;
    }
}
