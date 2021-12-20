package com.snp.takealook.api.controller.user;

import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.user.UserDTO;
import com.snp.takealook.api.service.S3Uploader;
import com.snp.takealook.api.service.user.UserService;
import com.snp.takealook.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.source.spi.JdbcDataType;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    // 소셜 로그인, 회원 가입
    @PostMapping("/login")
    public ResponseDTO.UserResponse login(HttpServletResponse response, @RequestBody Map<String, Object> data) {
        return userService.login(response, data, (String) data.get("provider"));
    }

    // 로그인 갱신
    @GetMapping("/loadUser")
    public ResponseDTO.UserResponse loadUser(@AuthenticationPrincipal PrincipalDetails principal, HttpServletResponse resp) throws IOException {
        return userService.loadUser(principal, resp);
    }

    // 회원정보 수정
    @PostMapping(value = "/user/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long update(@PathVariable Long userId,
                       @RequestPart(value = "userInfo") UserDTO.Update dto,
                       @RequestPart(value = "profileImg") MultipartFile file) throws IOException {
        return userService.update(userId, dto, file);
    }

    // 회원정보 조회
    @GetMapping("/user/{userId}")
    public ResponseDTO.UserResponse findOne(@PathVariable Long userId) {
        return userService.findOne(userId);
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
