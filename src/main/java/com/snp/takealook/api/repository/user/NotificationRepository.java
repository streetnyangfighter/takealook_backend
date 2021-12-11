package com.snp.takealook.api.repository.user;

import com.snp.takealook.api.domain.user.Notification;
import com.snp.takealook.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findNotificationsByUser(User user);
    List<Notification> findNotificationsByUserAndCheckedFalse(User user);
}
