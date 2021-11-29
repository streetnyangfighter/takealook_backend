package com.snp.takealook.controller.user;

import com.snp.takealook.domain.user.User;
import com.snp.takealook.dto.user.UserDTO;
import com.snp.takealook.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    // 회원 가입 후 추가 정보 입력
    @PutMapping("/user/initial/{id}")
    public Long updateLoginDetail(@PathVariable Long id, @RequestBody UserDTO.Update dto) {
        return userService.updateLoginDetail(id, dto);
    }

    // 닉네임 중복체크
    @PostMapping("/user/check")
    public boolean check(@RequestBody UserDTO.Check dto) {
        return userService.ckeckNickname(dto.getNickname());
    }

    // 회원정보 조회
    @GetMapping("user/{id}")
    public User getInfo(@PathVariable Long id) {
        return userService.getInfo(id);
    }

    // 회원정보 수정
    @PutMapping("user/{id}")
    public Long updateInfo(@PathVariable Long id, @RequestBody UserDTO.Update dto) {
        return userService.updateInfo(id, dto);
    }

    // 회원 삭제
    @PatchMapping("user/{id}")
    public Long delete(@PathVariable Long id) {
        userService.delete(id);
        return id;
    }

}
