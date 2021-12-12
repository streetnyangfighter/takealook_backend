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

    @GetMapping("/user/{userId}/notifications")
    public List<ResponseDTO.NotificationListResponse> findAllByUserId(@PathVariable Long userId) {
        return notificationService.findAllByUserId(userId);
    }

    @GetMapping("user/{userId}/unchecked-notifications")
    public Boolean hasUncheckedNotifation(@PathVariable Long userId) {
        return notificationService.hasUncheckedNotifation(userId);
        // 결과값이 true 이면 확인하지 않은 알림이 있음
        // 결과값이 fasle 이면 확인하지 않은 알림이 없음
    }
}
