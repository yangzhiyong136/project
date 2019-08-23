package com.learning.project.enums;

/**
 * @author Youngz
 * @date 2019/8/19 - 21:05
 */
public enum NotificationStatusEnum {
    UNREAD(0),READ(1);
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
