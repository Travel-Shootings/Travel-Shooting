package com.sparta.travelshooting.notification.service;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.notification.dto.NotificationResponseDto;
import com.sparta.travelshooting.notification.entity.Notification;
import com.sparta.travelshooting.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public List<NotificationResponseDto> getUncheckedNotifications(Long userId) {
        return notificationRepository.findAllByUserIdAndIsReadFalse(userId)
                .stream()
                .map(NotificationResponseDto::new)
                .toList();
    }

    @Override
    public List<NotificationResponseDto> getCheckedNotifications(Long userId) {
        return notificationRepository.findAllByUserIdAndIsReadTrue(userId)
                .stream()
                .map(NotificationResponseDto::new)
                .toList();
    }

    @Override
    @Transactional
    public ApiResponseDto readNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new NullPointerException("Not found Notification"));
        notification.read();

        return new ApiResponseDto("알림 확인", HttpStatus.OK.value());
    }

    @Override
    @Transactional
    public ApiResponseDto deleteNotification(Long userId) {
        notificationRepository.deleteAllByUserIdAndIsReadTrue(userId);

        return new ApiResponseDto("알림 삭제", HttpStatus.OK.value());
    }
}
