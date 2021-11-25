package com.snp.takealook.service.user;

import com.snp.takealook.domain.user.Notification;
import com.snp.takealook.domain.user.User;
import com.snp.takealook.dto.user.NotificationDTO;
import com.snp.takealook.repository.user.NotificationRepository;
import com.snp.takealook.repository.user.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Notification> getNotificationByUserId(NotificationDTO.Get dto) throws NotFoundException {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new NotFoundException("User with id: " + dto.getUserId() + " is not valid"));

        return notificationRepository.findNotificationsByUser(user);
    }
}
