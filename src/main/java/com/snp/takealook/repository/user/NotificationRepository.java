package com.snp.takealook.repository.user;

import com.snp.takealook.domain.user.Notification;
import com.snp.takealook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findNotificationsByUser(User user);
}
