package com.snp.takealook.api.controller.user;

import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.user.UserDTO;
import com.snp.takealook.api.service.user.UserService;
import com.snp.takealook.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
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
    public ResponseDTO.UserResponse loadUser(HttpServletResponse response, @AuthenticationPrincipal PrincipalDetails principal, HttpServletResponse resp) throws IOException {
        return userService.loadUser(response, principal, resp);
    }

    // 닉네임 중복체크
    @PostMapping("/user/check")
    public boolean check(@RequestBody UserDTO.Check dto) {
        return userService.ckeckNickname(dto.getNickname());
    }

    // 회원정보 수정
    @PostMapping(value = "/user/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long update(@AuthenticationPrincipal PrincipalDetails principal,
                       @RequestPart(value = "userInfo") UserDTO.Update dto,
                       @RequestPart(value = "profileImg", required = false) MultipartFile file) throws IOException {
        User user = principal.getUser();

        return userService.update(user.getId(), dto, java.util.Optional.ofNullable(file));
    }

    // 회원정보 조회
    @GetMapping("/user/{userId}")
    public ResponseDTO.UserResponse findOne(@AuthenticationPrincipal PrincipalDetails principal) {
        User user = principal.getUser();

        return userService.findOne(user.getId());
    }

    // 회원 삭제
    @PatchMapping("/user/{userId}/delete")
    public Long delete(@AuthenticationPrincipal PrincipalDetails principal) {
        User user = principal.getUser();

        return userService.delete(user.getId());
    }

    // 회원 복구
    @PatchMapping("/user/{userId}/restore")
    public Long restore(@AuthenticationPrincipal PrincipalDetails principal) {
        User user = principal.getUser();

        return userService.restore(user.getId());
    }

}
