package com.sparta.travelshooting.notification.service;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.notification.dto.NotificationResponseDto;
import com.sparta.travelshooting.notification.entity.Notify;
import com.sparta.travelshooting.notification.repository.NotificationRepository;
import com.sparta.travelshooting.user.entity.User;
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
    public List<NotificationResponseDto> getUncheckedNotifications(User user) {
        return notificationRepository.findAllByUserIdAndIsReadFalse(user.getId())
                .stream()
                .map(NotificationResponseDto::new)
                .toList();
    }

    @Override
    @Transactional
    public ApiResponseDto readNotification(Long notificationId) {
        Notify notify = notificationRepository.findById(notificationId).orElseThrow(() -> new NullPointerException("Not found Notification"));
        notify.read();

        return new ApiResponseDto("알림 확인", HttpStatus.OK.value());
    }

    @Override
    public List<NotificationResponseDto> getCheckedNotifications(User user) {
        return notificationRepository.findAllByUserIdAndIsReadTrue(user.getId())
                .stream()
                .map(NotificationResponseDto::new)
                .toList();
    }

    @Override
    @Transactional
    public ApiResponseDto deleteNotification(User user) {
        notificationRepository.deleteAllByUserIdAndIsReadTrue(user.getId());

        return new ApiResponseDto("알림 삭제", HttpStatus.OK.value());
    }
}
