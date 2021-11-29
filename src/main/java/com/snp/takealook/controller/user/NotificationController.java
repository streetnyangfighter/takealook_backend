package com.snp.takealook.controller.user;

import com.snp.takealook.dto.ResponseDTO;
import com.snp.takealook.service.user.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/notification/userid/{userId}")
    public List<ResponseDTO.NotificationListResponse> findAllByUserId(@PathVariable Long userId) {
        List<ResponseDTO.NotificationListResponse> list = null;
        try{
            list = notificationService.findAllByUserId(userId);
        }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }
}
