package com.snp.takealook.controller.user;

import com.snp.takealook.dto.RequestDTO;
import com.snp.takealook.dto.user.UserDTO;
import com.snp.takealook.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PatchMapping("/user/initial/{id}")
    public Long updateLoginDetail(@PathVariable Long id, @RequestBody UserDTO.InitialUpdate dto) {
        return userService.updateLoginDetail(id, dto);
    }

    @PatchMapping("user/{id}")
    public Long delete(@PathVariable Long id) {
        userService.delete(id);
        return id;
    }

    @PutMapping("/user/locations/{id}")
    public Long updateLocations(@PathVariable Long id, @RequestBody List<UserDTO.LocationList> dtoList) {
        userService.updateLocations(id, dtoList);
        return id;
    }

}
