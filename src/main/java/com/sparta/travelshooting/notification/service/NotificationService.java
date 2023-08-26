package com.sparta.travelshooting.notification.service;

import com.sparta.travelshooting.notification.dto.NotificationResponseDto;
import com.sparta.travelshooting.user.entity.User;

import java.util.List;

public interface NotificationService {
    List<NotificationResponseDto> getUncheckedNotifications(User user);
}
