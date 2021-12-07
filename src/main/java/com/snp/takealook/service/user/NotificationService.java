package com.snp.takealook.service.user;

import com.snp.takealook.domain.user.Notification;
import com.snp.takealook.domain.user.User;
import com.snp.takealook.dto.ResponseDTO;
import com.snp.takealook.dto.user.NotificationDTO;
import com.snp.takealook.repository.user.NotificationRepository;
import com.snp.takealook.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(Long userId, NotificationDTO.Create dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));

        return notificationRepository.save(dto.toEntity(user)).getId();
    }

    @Transactional
    public Long check(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Notification with id: " + id + " is not valid"));

        notification.check();

        return id;
    }

    @Transactional(readOnly = true)
    public List<ResponseDTO.NotificationListResponse> findAllByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));

        return notificationRepository.findNotificationsByUser(user).stream()
                .map(ResponseDTO.NotificationListResponse::new)
                .collect(Collectors.toList());
    }
}
