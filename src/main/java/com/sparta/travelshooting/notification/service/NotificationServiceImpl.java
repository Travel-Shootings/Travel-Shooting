package com.sparta.travelshooting.notification.service;

import com.sparta.travelshooting.notification.dto.NotificationResponseDto;
import com.sparta.travelshooting.notification.repository.NotificationRepository;
import com.sparta.travelshooting.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepository;

    @Override
    public List<NotificationResponseDto> getUncheckedNotifications(User user) {
        return notificationRepository.findAllByUserIdAndIsReadFalse(user.getId())
                .stream()
                .map(NotificationResponseDto::new)
                .toList();
    }
}
