package com.snp.takealook.config.auth;

import com.snp.takealook.config.auth.dto.OAuthAttributes;
import com.snp.takealook.config.auth.dto.SessionUser;
import com.snp.takealook.domain.user.User;
import com.snp.takealook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 현재 로그인 진행 중인 서비스를 구분하는 코드 -> 서비스 여러 개 사용할 때
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // Oauth2 로그인 시 키가 되는 필드값. (= Primary Key)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes, registrationId);
        // 세션에 사용자 정보를 저장하기 위한 DTO 클래스 -> SessionUser
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    // 동일한 email & registrationId가 있는지 확인 뒤, 없다면 새로 추가/있다면 정보변경
    private User saveOrUpdate(OAuthAttributes attributes, String registrationId) {
        User user = userRepository.findByLoginIdAndLoginType(attributes.getLoginId(), registrationId)
                    .map(entity -> entity.update(attributes.getNickname() ,attributes.getImage()))
                    .orElse(attributes.toEntity(registrationId));

        return userRepository.save(user);
    }
}

