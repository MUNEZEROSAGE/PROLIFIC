package com.finalProject.repository;

import com.finalProject.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Find notifications for a citizen
    Page<Notification> findByCitizenId(Long citizenId, Pageable pageable);

    // Filter by read/unread status
    Page<Notification> findByIsRead(boolean isRead, Pageable pageable);
}