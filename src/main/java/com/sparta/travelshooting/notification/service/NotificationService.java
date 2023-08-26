package com.sparta.travelshooting.notification.service;

import com.sparta.travelshooting.common.entity.ApiResponseDto;
import com.sparta.travelshooting.notification.dto.NotificationResponseDto;
import com.sparta.travelshooting.user.entity.User;

import java.util.List;

public interface NotificationService {
    /**
     * 읽지않은 알림 조회 API
     * @param user 로그인 된 유저 정보
     * @return 읽지않은 알림 List
     */
    List<NotificationResponseDto> getUncheckedNotifications(User user);

    /**
     * 알림 확인 API
     * @param notificationId 알림 데이터
     * @return 알림 확인
     */
    ApiResponseDto readNotification(Long notificationId);
}
