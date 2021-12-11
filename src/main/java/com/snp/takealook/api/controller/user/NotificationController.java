package com.snp.takealook.api.controller.user;

import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.service.user.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @PatchMapping("user/{userId}/notification/{notificationId}")
    public Long check(@PathVariable Long notificationId) {
        return notificationService.check(notificationId);
    }

    @GetMapping("user/{userId}/notifications")
    public List<ResponseDTO.NotificationListResponse> findAllByUserId(@PathVariable Long userId) {
        return notificationService.findAllByUserId(userId);
    }
}
