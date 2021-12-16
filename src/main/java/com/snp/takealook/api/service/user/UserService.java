package com.snp.takealook.api.service.user;

import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.oauth.OauthUserInfo;
import com.snp.takealook.api.dto.user.UserDTO;
import com.snp.takealook.api.repository.user.UserRepository;
import com.snp.takealook.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    // 회원 idx로 찾기
    @Transactional(readOnly = true)
    public ResponseDTO.UserResponse findById(Long id) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + id + " is not valid"));

        return new ResponseDTO.UserResponse(entity);
    }

    // 회원 가입 후 추가 정보 입력 (닉네임 변경, 휴대폰번호, 이미지)
    @Transactional(rollbackFor = {RuntimeException.class})
    public Long updateLoginDetail(Long id, UserDTO.Update dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없습니다."));

        user.updateDetail(dto.getNickname(), dto.getPhone(), dto.getImage());

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
    @Transactional(rollbackFor = {RuntimeException.class})
    public Long delete(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없습니다."));

        return user.delete().getId();
    }

    // 회원 복구
    @Transactional(rollbackFor = {RuntimeException.class})
    public Long restore(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없습니다."));

        return user.restore().getId();
    }

    // 소셜 로그인
    public UserDTO.LoginInfo userLogin(String code, String provider) throws Exception {
        OauthUserInfo oauthUserInfo = null;
        UserDTO.Register userDTO = null;
        UserDTO.LoginInfo result = null;

        try {
            Class social = Class.forName("com.snp.takealook.api.service.user.Social");
            Method method = social.getDeclaredMethod(provider, String.class);
            oauthUserInfo = (OauthUserInfo) method.invoke(null, code);

            System.out.println(oauthUserInfo.getNickname());
            System.out.println(oauthUserInfo.getLoginId());
            System.out.println(oauthUserInfo.getImage());
            System.out.println(oauthUserInfo.getLoginType());

            userDTO = UserDTO.Register.builder()
                    .loginId(oauthUserInfo.getLoginId())
                    .nickname(oauthUserInfo.getNickname())
                    .image(oauthUserInfo.getImage())
                    .loginType(oauthUserInfo.getLoginType())
                    .build();

            System.out.println("-------------------------- userDTO");

            User user = userRepository.findByLoginId(userDTO.getLoginId());

            if (Objects.isNull(user)) {
                user = userRepository.save(userDTO.toEntity());
            }

            result = UserDTO.LoginInfo.builder()
                    .id(user.getId())
                    .loginId(user.getLoginId())
                    .nickname(user.getNickname())
                    .build();

            if (user.getPhone() == null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user.getId(), null, AuthorityUtils.createAuthorityList("USER"));


                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = tokenProvider.createToken(authentication);
                result.setAccessToken(jwt);
            }


        } catch (Exception e) {

        }

        return result;
    }

}
