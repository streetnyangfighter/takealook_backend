package com.snp.takealook.controller;

import com.snp.takealook.dto.RequestDTO;
import com.snp.takealook.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PatchMapping("/detail/{id}")
    public Long updateLoginDetail(@PathVariable Long id, @RequestBody RequestDTO.UserUpdate userUpdate) {
        return userService.updateLoginDetail(id, userUpdate);
    }

}
