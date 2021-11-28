package com.snp.takealook.controller.user;

import com.snp.takealook.dto.ResponseDTO;
import com.snp.takealook.dto.user.NotificationDTO;
import com.snp.takealook.service.user.NotificationService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/notification/userid")
    public List<ResponseDTO.NotificationListResponse> findAllByUserId(NotificationDTO.Get dto) {
        List<ResponseDTO.NotificationListResponse> list = null;
        try{
            list = notificationService.findAllByUserId(dto);
        }catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }
}
