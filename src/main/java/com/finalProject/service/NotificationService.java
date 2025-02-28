package com.finalProject.service;

import com.finalProject.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {
    Notification saveNotification(Notification notification);
    Page<Notification> getAllNotifications(Pageable pageable);
    Notification getNotificationById(Long id);
    void deleteNotification(Long id);
    Notification updateNotification(Long id, Notification notification);
    Page<Notification> findByCitizenId(Long citizenId, Pageable pageable);
    Page<Notification> findByIsRead(boolean isRead, Pageable pageable);
}