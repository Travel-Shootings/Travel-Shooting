package com.sparta.travelshooting.notification.dto;

import com.sparta.travelshooting.notification.entity.Notification;
import lombok.Getter;

@Getter
public class NotificationResponseDto {
    private String message;

    public NotificationResponseDto(Notification notification) {
        this.message = notification.getMessage();
    }
}
