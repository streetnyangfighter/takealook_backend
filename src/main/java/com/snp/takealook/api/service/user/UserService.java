package com.snp.takealook.api.service.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.snp.takealook.api.domain.user.ProviderType;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.oauth.GoogleUserInfo;
import com.snp.takealook.api.dto.oauth.KakaoUserInfo;
import com.snp.takealook.api.dto.oauth.OAuth2UserInfo;
import com.snp.takealook.api.dto.user.UserDTO;
import com.snp.takealook.api.repository.user.UserRepository;
import com.snp.takealook.api.service.S3Uploader;
import com.snp.takealook.config.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final S3Uploader s3Uploader;

    // 소셜 로그인
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO.UserResponse login(HttpServletResponse response, Map<String, Object> data, String provider) {

        Boolean success = false;

        OAuth2UserInfo userInfo = null;
        ProviderType providerType = null;

//        userInfo = new GoogleUserInfo((Map<String, Object>) data.get("profileObj"));
//        providerType = ProviderType.GOOGLE;

        System.out.println("*** " + data);
        System.out.println();
        Map<String, Object> aaa = (Map<String, Object>) data.get("object");
        System.out.println("*** " + aaa);
        System.out.println();
        System.out.println("*** " + (Map<String, Object>) aaa.get("profile"));

        if (provider.equals("google")) {
            Map<String, Object> info = (Map<String, Object>) data.get("object");
            userInfo = new GoogleUserInfo((Map<String, Object>) info.get("profileObj"));
            providerType = ProviderType.GOOGLE;
        } else if (provider.equals("kakao")) {
            Map<String, Object> info = (Map<String, Object>) data.get("object");
            userInfo = new KakaoUserInfo((Map<String, Object>) info.get("profile"));
            providerType = ProviderType.KAKAO;
        }

        System.out.println(userInfo.getUsername());
        System.out.println(providerType);

        User userEntity = userRepository.findByUsername(userInfo.getUsername());
        UUID uuid = UUID.randomUUID();
        String encPassword = encoder.encode(uuid.toString());

        if (userEntity == null) { // 최초 로그인 -> 회원가입

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
        String jwtToken = JWT.create()
                .withSubject(userEntity.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRE_TIME)) //토큰의 유효기간 현재시간으로부터 1시간
                .withClaim("id", userEntity.getId()) //인증에 필요한 정보
                .withClaim("username", userEntity.getNickname())
                .sign(Algorithm.HMAC256(JwtProperties.SECRET));

        response.addHeader(JwtProperties.TOKEN_HAEDER, JwtProperties.TOKEN_PRIFIX + jwtToken);
        System.out.println("*** " + jwtToken);
        System.out.println("*** " + success);
        System.out.println(response);

        return new ResponseDTO.UserResponse(userEntity);
    }

    // 회원정보 수정
    @Transactional(rollbackFor = Exception.class)
    public Long update(Long userId, UserDTO.Update dto, MultipartFile file) throws IOException {
        String profileImage = s3Uploader.upload(file, "static");
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));

        if (user.getImage().startsWith("https://takealook-bucket")) {
            s3Uploader.fileDelete(user.getImage());
        }

        return user.update(dto.getNickname(), profileImage).getId();
    }

    // 회원정보 조회
    @Transactional(readOnly = true)
    public ResponseDTO.UserResponse findOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));

        return new ResponseDTO.UserResponse(user);
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

}
