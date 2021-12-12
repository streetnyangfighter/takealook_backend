package com.snp.takealook.api.service.user;

import com.snp.takealook.api.domain.community.Post;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.user.UserDTO;
import com.snp.takealook.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

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

//    //회원 정보 수정
//    @Transactional
//    public Long updateInfo(Long id, UserDTO.Update dto, List<UserDTO.LocationList> dtoList) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없습니다."));
//
//        user.updateDetail(dto.getNickname(), dto.getPhone(), dto.getImage());
//
//        userLocationRepository.deleteAll(user.getUserLocationList());
//
//        List<UserLocation> list = dtoList.stream()
//                .map(v -> UserLocation.builder()
//                        .user(user)
//                        .sido(v.getSido())
//                        .gugun(v.getGugun())
//                        .dong(v.getDong())
//                        .build())
//                .collect(Collectors.toList());
//
//        user.updateLocations(list);
//
//        return id;
//    }

    // 회원 탈퇴
    @Transactional
    public Long delete(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없습니다."));

        return user.delete().getId();
    }

    @Transactional
    public void deleteHard(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없습니다."));
        userRepository.delete(user);
    }

    // 회원 복구
    @Transactional
    public Long restore(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없습니다."));

        return user.restore().getId();
    }

    @Transactional
    public void testHardDelete() {
//        LocalDateTime limitDate = LocalDateTime.now().minusMonths(3);
        LocalDateTime limitDate = LocalDateTime.now().minusMinutes(1);

        List<User> userList = userRepository.findUsersByModifiedAtBeforeAndDflagTrue(limitDate);

        for (User user : userList) {

            List<Post> postList = user.getPostList();

            for (Post post : postList) {
                post.setWriterNull();
            }
        }

        System.out.println(userList.size());
        userRepository.deleteAll(userRepository.findUsersByModifiedAtBeforeAndDflagTrue(limitDate));

//        System.out.println(userRepository.hardDelete(limitDate));
    }
//
//    @Transactional
//    public void updateLocations(Long id, List<UserDTO.LocationList> dtoList) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없습니다."));
//
//        if (user.getUserLocationList().size() != 0) {
//            userLocationRepository.deleteAll(user.getUserLocationList());
//        }
//
//        List<UserLocation> list = dtoList.stream()
//                .map(v -> UserLocation.builder()
//                        .user(user)
//                        .sido(v.getSido())
//                        .gugun(v.getGugun())
//                        .dong(v.getDong())
//                        .build())
//                .collect(Collectors.toList());
//
//        user.updateLocations(list);
//    }

}
