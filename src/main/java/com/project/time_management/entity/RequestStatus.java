package com.project.time_management.entity;

public enum RequestStatus {
    CREATED("created"),
    APPROVED("approved"),
    DECLINED("declined");

    private final String value;
    RequestStatus(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
