package com.sparta.travelshooting.notification.dto;

import com.sparta.travelshooting.notification.entity.Notify;
import lombok.Getter;

@Getter
public class NotificationResponseDto {
    private String message;

    public NotificationResponseDto(Notify notify) {
        this.message = notify.getMessage();
    }
}
