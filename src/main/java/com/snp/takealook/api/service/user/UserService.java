package com.snp.takealook.api.service.user;

import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.domain.user.UserLocation;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.user.UserDTO;
import com.snp.takealook.api.repository.user.UserLocationRepository;
import com.snp.takealook.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserLocationRepository userLocationRepository;

    // 회원 idx로 찾기
    @Transactional(readOnly = true)
    public ResponseDTO.UserResponse findById(Long id) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + id + " is not valid"));

        return new ResponseDTO.UserResponse(entity);
    }

    // 회원 가입 후 추가 정보 입력 (닉네임 변경, 휴대폰번호, 이미지)
    @Transactional
    public Long updateLoginDetail(Long id, UserDTO.Update dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없습니다."));

        user.updateDetail(dto.getNickname(), dto.getPhone(), dto.getImage());

        return id;
    }

    // 닉네임 중복 체크
    public boolean ckeckNickname(String nickname) {
        boolean result = false;
        User user = userRepository.findByNickname(nickname);

        if(user == null) {
            result = true;
        }

        // 닉네임 중복 : false, 사용가능 : true
        return result;
    }

    // 회원정보 조회
    @Transactional(readOnly = true)
    public User getInfo(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없습니다."));

        return user;
    }

    //회원 정보 수정
    @Transactional
    public Long updateInfo(Long id, UserDTO.Update dto, List<UserDTO.LocationList> dtoList) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없습니다."));

        user.updateDetail(dto.getNickname(), dto.getPhone(), dto.getImage());

        userLocationRepository.deleteAll(user.getUserLocationList());

        List<UserLocation> list = dtoList.stream()
                .map(v -> UserLocation.builder()
                        .user(user)
                        .sido(v.getSido())
                        .gugun(v.getGugun())
                        .dong(v.getDong())
                        .build())
                .collect(Collectors.toList());

        user.updateLocations(list);

        return id;
    }

    // 회원 탈퇴
    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없습니다."));

        user.delete(true);
    }

    @Transactional
    public void updateLocations(Long id, List<UserDTO.LocationList> dtoList) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없습니다."));

        if (user.getUserLocationList().size() != 0) {
            userLocationRepository.deleteAll(user.getUserLocationList());
        }

        List<UserLocation> list = dtoList.stream()
                .map(v -> UserLocation.builder()
                        .user(user)
                        .sido(v.getSido())
                        .gugun(v.getGugun())
                        .dong(v.getDong())
                        .build())
                .collect(Collectors.toList());

        user.updateLocations(list);
    }

}
