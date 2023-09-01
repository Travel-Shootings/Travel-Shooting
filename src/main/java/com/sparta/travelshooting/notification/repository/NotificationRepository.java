package com.sparta.travelshooting.notification.repository;

import com.sparta.travelshooting.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUserIdAndIsReadFalse(Long id);

    List<Notification> findAllByUserIdAndIsReadTrue(Long id);

    void deleteAllByUserIdAndIsReadTrue(Long id);
}
