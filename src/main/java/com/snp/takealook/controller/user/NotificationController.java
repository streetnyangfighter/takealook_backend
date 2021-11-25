package com.snp.takealook.controller.user;

import com.snp.takealook.domain.user.Notification;
import com.snp.takealook.dto.ResponseDTO;
import com.snp.takealook.dto.user.NotificationDTO;
import com.snp.takealook.service.user.NotificationService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/notification")
    public ResponseDTO.NotificationListResponse getNotificationListByUserId(@RequestBody NotificationDTO.Get dto) {
        System.out.println("--- 회원 알림 리스트 조회 시도 ---");

        boolean success = false;
        String msg = null;
        List<Notification> notificationList = null;
        try{
            notificationList = notificationService.getNotificationByUserId(dto);
            success = true;
        }catch(NotFoundException e) {
            System.out.println("--- 회원 알림 리스트 조회 실패 ---");
            System.out.println(e.getMessage());
            msg = e.getMessage();
        }

        return new ResponseDTO.NotificationListResponse(success, msg, notificationList);
    }
}
