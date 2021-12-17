package com.snp.takealook.api.controller.user;

import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.user.UserDTO;
import com.snp.takealook.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    // 소셜 로그인, 회원 가입
    @PostMapping("/login")
    public ResponseDTO.UserResponse login(HttpServletResponse response, @RequestBody Map<String, Object> data, String provider) {
        return userService.login(response, data, provider);
    }

    // 회원정보 수정
//    @PatchMapping("/user/{userId}")
//    public

    // 회원정보 조회
    @GetMapping("/user/{id}")
    public User getInfo(@PathVariable Long id) {
        return userService.getInfo(id);
    }

    // 회원 삭제
    @PatchMapping("/user/{userId}/delete")
    public Long delete(@PathVariable Long userId) {
        return userService.delete(userId);
    }

    // 회원 복구
    @PatchMapping("/user/{userId}/restore")
    public Long restore(@PathVariable Long userId) {
        return userService.restore(userId);
    }

}
