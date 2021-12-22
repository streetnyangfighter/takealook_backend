package com.snp.takealook.api.controller.user;

import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.service.user.NotificationService;
import com.snp.takealook.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/user/{userId}/notifications")
    public List<ResponseDTO.NotificationListResponse> findAllByUserId(@AuthenticationPrincipal PrincipalDetails principal) {
        User user = principal.getUser();

        return notificationService.findAllByUserId(user.getId());
    }

    @GetMapping("/user/{userId}/notification/{notiId}")
    public Boolean clickNotification(@PathVariable Long notiId) {
        return notificationService.checkNotification(notiId);
    }

    @GetMapping("/user/{userId}/notification/unchecked")
    public Long getUncheckedNotifications(@AuthenticationPrincipal PrincipalDetails principal) {
        User user = principal.getUser();

        return notificationService.getUncheckedNotifications(user.getId());
    }
}
