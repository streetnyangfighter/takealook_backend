package com.snp.takealook.controller.user;

import com.snp.takealook.dto.ResponseDTO;
import com.snp.takealook.dto.user.UserLocationDTO;
import com.snp.takealook.service.user.UserLocationService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserLocationController {

    private final UserLocationService userLocationService;

    @PostMapping("/userlocation")
    public Long save(UserLocationDTO.Create dto) {
        List<ResponseDTO.UserLocationListResponse> list = findAllByUserId(new UserLocationDTO.Get(dto.getUser().getId()));
        if (list.size() < 3) {
            return userLocationService.save(dto);
        }

        return null;
    }

    @DeleteMapping("/userlocation")
    public Long delete(UserLocationDTO.Delete dto) {
        try{
            userLocationService.delete(dto);
        }catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return dto.getId();
    }

    @GetMapping("/userlocation")
    public List<ResponseDTO.UserLocationListResponse> findAllByUserId(UserLocationDTO.Get dto) {
        List<ResponseDTO.UserLocationListResponse> list = null;
        try {
            list = userLocationService.findAllByUserId(dto);
        }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }
}
