package com.snp.takealook.controller.user;

import com.snp.takealook.dto.ResponseDTO;
import com.snp.takealook.service.user.UserLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class UserLocationController {

    private final UserLocationService userLocationService;

    @GetMapping("/userlocation/{userId}")
    public List<ResponseDTO.UserLocationListResponse> findAllByUserId(@PathVariable Long userId) {
        List<ResponseDTO.UserLocationListResponse> list = null;
        try {
            list = userLocationService.findAllByUserId(userId);
        }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }
}
