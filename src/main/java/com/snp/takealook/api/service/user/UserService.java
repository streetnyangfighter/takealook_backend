package com.snp.takealook.api.service.user;

import com.snp.takealook.api.domain.user.ProviderType;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.oauth.GoogleUserInfo;
import com.snp.takealook.api.dto.oauth.KakaoUserInfo;
import com.snp.takealook.api.dto.oauth.NaverUserInfo;
import com.snp.takealook.api.dto.oauth.OAuth2UserInfo;
import com.snp.takealook.api.dto.user.UserDTO;
import com.snp.takealook.api.repository.user.UserRepository;
import com.snp.takealook.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final TokenProvider tokenProvider;

    // 회원 idx로 찾기
    @Transactional(readOnly = true)
    public ResponseDTO.UserResponse findById(Long id) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + id + " is not valid"));

        return new ResponseDTO.UserResponse(entity);
    }

    // 회원 가입 후 추가 정보 입력 (닉네임 변경, 휴대폰번호, 이미지)
    @Transactional(rollbackFor = Exception.class)
    public Long updateLoginDetail(Long id, UserDTO.Update dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없습니다."));

        user.updateDetail(dto.getNickname(), dto.getImage());

        return id;
    }

    // 닉네임 중복 체크
    @Transactional(readOnly = true)
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

    // 회원 탈퇴
    @Transactional(rollbackFor = Exception.class)
    public Long delete(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없습니다."));

        return user.delete().getId();
    }

    // 회원 복구
    @Transactional(rollbackFor = Exception.class)
    public Long restore(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없습니다."));

        return user.restore().getId();
    }

    // 소셜 로그인
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO.JwtTokenResponse login(Map<String, Object> data, String provider) {

        Boolean success = false;

        OAuth2UserInfo userInfo = null;
        ProviderType providerType = null;

        if (provider.equals("Google")) {
            userInfo = new GoogleUserInfo((Map<String, Object>) data.get("profileObj"));
            providerType = ProviderType.GOOGLE;
        } else if (provider.equals("Kakao")) {
            userInfo = new KakaoUserInfo(data);
            providerType = ProviderType.KAKAO;
        } else if (provider.equals("Naver")) {
            userInfo = new NaverUserInfo(data);
            providerType = ProviderType.NAVER;
        }

        System.out.println(userInfo.getUsername());
        System.out.println(provider);
        System.out.println(providerType);

        User userEntity = userRepository.findByUsername(userInfo.getUsername());
        UUID uuid = UUID.randomUUID();
        String encPassword = encoder.encode(uuid.toString());

        if (userEntity == null) {
            // 최초 로그인 -> 회원가입

            User user = User.builder()
                    .username(userInfo.getUsername())
                    .password(encPassword)
                    .email(userInfo.getEmail())
                    .nickname(userInfo.getNickname())
                    .image(userInfo.getImage())
                    .providerType(providerType)
                    .build();

            userEntity = userRepository.save(user);
            success = true;
        }

        // 토큰 만들기
        String token = tokenProvider.createToken(userEntity);
        System.out.println("*** " + token);
        System.out.println("*** " + success);

        return new ResponseDTO.JwtTokenResponse(success, token);
    }
}
