package com.snp.takealook.controller.user;

import com.snp.takealook.dto.ResponseDTO;
import com.snp.takealook.dto.user.UserLocationDTO;
import com.snp.takealook.service.user.UserLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserLocationController {

    private final UserLocationService userLocationService;

    @PostMapping("/userlocation/{userId}")
    public Long save(@PathVariable Long userId, UserLocationDTO.Create dto) {
        List<ResponseDTO.UserLocationListResponse> list = findAllByUserId(userId);
        if (list.size() < 3) {
            return userLocationService.save(dto);
        }

        return null;
    }

    @DeleteMapping("/userlocation/{id}")
    public Long delete(@PathVariable Long id) {
        try{
            userLocationService.delete(id);
        }catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

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
