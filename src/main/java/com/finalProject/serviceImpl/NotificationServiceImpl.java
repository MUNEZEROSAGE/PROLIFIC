package com.finalProject.serviceImpl;

import com.finalProject.model.Notification;
import com.finalProject.repository.NotificationRepository;
import com.finalProject.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Page<Notification> getAllNotifications(Pageable pageable) {
        return notificationRepository.findAll(pageable);
    }

    @Override
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public Notification updateNotification(Long id, Notification notification) {
        if (notificationRepository.existsById(id)) {
            notification.setId(id);
            return notificationRepository.save(notification);
        }
        throw new RuntimeException("Notification not found with id: " + id);
    }

    @Override
    public Page<Notification> findByCitizenId(Long citizenId, Pageable pageable) {
        return notificationRepository.findByCitizenId(citizenId, pageable);
    }

    @Override
    public Page<Notification> findByIsRead(boolean isRead, Pageable pageable) {
        return notificationRepository.findByIsRead(isRead, pageable);
    }
}