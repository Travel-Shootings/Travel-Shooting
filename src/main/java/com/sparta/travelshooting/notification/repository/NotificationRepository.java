package com.sparta.travelshooting.notification.repository;

import com.sparta.travelshooting.notification.entity.Notify;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notify, Long> {
    List<Notify> findAllByUserIdAndIsReadFalse(Long id);
}
