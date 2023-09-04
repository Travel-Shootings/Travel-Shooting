package com.sparta.travelshooting.notification.dto;

import com.sparta.travelshooting.notification.entity.Notification;
import lombok.Getter;

@Getter
public class NotificationResponseDto {
    private Long id;
    private String message;
    private Long postId;
    private Long reviewPostId;

    public NotificationResponseDto(Notification notification) {
        this.id = notification.getId();
        this.message = notification.getMessage();
        this.postId = notification.getPostId();
        this.reviewPostId = notification.getReviewPostId();
    }
}
