package com.snp.takealook.controller.user;

import com.snp.takealook.domain.user.User;
import com.snp.takealook.domain.user.UserLocation;
import com.snp.takealook.dto.ResponseDTO;
import com.snp.takealook.dto.user.UserDTO;
import com.snp.takealook.dto.user.UserLocationDTO;
import com.snp.takealook.service.user.UserLocationService;
import com.snp.takealook.service.user.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserLocationController {

    private final UserLocationService userLocationService;
    private final UserService userService;

    @PostMapping("/userlocation")
    public ResponseDTO.Create saveUserLocation(UserLocationDTO.Create dto) {
        System.out.println("--- 회원 활동지역 저장 시도 ---");

        boolean success = false;
        Long saveId = null;
        String msg = null;
        try{
            User user = userService.getUser(new UserDTO.Get(dto.getUserId()));
            if(user.getUserLocationList().size() < 3) {
                try {
                    System.out.println(1);
                    saveId = userLocationService.saveUserLocation(dto);
                    System.out.println(2);
                    success = true;
                    System.out.println(3);
                } catch (NotFoundException e) {
                    System.out.println("--- 회원 활동지역 저장 실패 ---");
                    System.out.println(e.getMessage());
                    msg = e.getMessage();
                }
            }else {
                msg = "Maximum storage value exceeded";
            }
        } catch(NotFoundException e) {
            System.out.println("--- 회원 조회 실패 ---");
            System.out.println(e.getMessage());
            msg = e.getMessage();
        }

        System.out.println(4);
        return new ResponseDTO.Create(saveId, success, msg);
    }

    @DeleteMapping("/userlocation")
    public ResponseDTO.Delete deleteUserLocation(UserLocationDTO.Delete dto) {
        System.out.println("--- 회원 활동지역 삭제 시도 ---");

        boolean success = false;
        String msg = null;
        try{
            userLocationService.deleteUserLocation(dto);
            success = true;
        } catch (NotFoundException e) {
            System.out.println("--- 회원 활동지역 삭제 실패 ---");
            System.out.println(e.getMessage());
            msg = e.getMessage();
        }

        return new ResponseDTO.Delete(success, msg);
    }

    @GetMapping("/userlocation")
    public ResponseDTO.UserLocationListResponse getUserLocationListByUserId(UserLocationDTO.Get dto) {
        System.out.println("--- 회원 활동지역 리스트 조회 시도 ---");

        boolean success = false;
        String msg = null;
        List<UserLocation> userLocationList = null;
        try{
            userLocationList = userLocationService.getUserLocationListByUserId(dto);
            success = true;
        } catch(NotFoundException e) {
            System.out.println("--- 회원 활동지역 리스트 조회 실패 ---");
            System.out.println(e.getMessage());
            msg = e.getMessage();
        }

        return new ResponseDTO.UserLocationListResponse(success, msg, userLocationList);
    }
}
