package com.sparta.travelshooting.notification.controller;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.notification.dto.NotificationResponseDto;
import com.sparta.travelshooting.notification.service.NotificationService;
import com.sparta.travelshooting.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/notifications/")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // 확인하지 않은 알림 조회 API
    @GetMapping("/{userId}/uncheck")
    public ResponseEntity<List<NotificationResponseDto>> uncheckedNotifications(@PathVariable Long userId) {
        List<NotificationResponseDto> notifications = notificationService.getUncheckedNotifications(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    // 확인한 알림 조회 API
    @GetMapping("/{userId}/check")
    public ResponseEntity<List<NotificationResponseDto>> checkedNotifications(@PathVariable Long userId) {
        List<NotificationResponseDto> notifications = notificationService.getCheckedNotifications(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    // 알림 확인 API
    @PostMapping("/{notificationId}/read")
    public ResponseEntity<ApiResponseDto> readNotification(@PathVariable Long notificationId) {
        ApiResponseDto apiResponseDto = notificationService.readNotification(notificationId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 확인한 알림 삭제 API
    @DeleteMapping("/{userId}/check/delete")
    public ResponseEntity<ApiResponseDto> deleteNotification(@PathVariable Long userId) {
        ApiResponseDto apiResponseDto = notificationService.deleteNotification(userId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }
}
