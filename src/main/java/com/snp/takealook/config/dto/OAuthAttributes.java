package com.snp.takealook.config.dto;

import com.snp.takealook.api.domain.user.Role;
import com.snp.takealook.api.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String nickname;
    private String loginId;
    private String image;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String nickname, String loginId, String image) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.nickname = nickname;
        this.loginId = loginId;
        this.image = image;
    }

    // OAuth2User에서 반환하는 Map 사용자 정보를 받아 각 attribute로 변환
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if("kakao".equals(registrationId)){
            return ofKakao("id", attributes);
        } else if ("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nickname((String) attributes.get("name"))
                .loginId((String) attributes.get("email"))
                .image((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String,Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        Map<String,Object> profile = (Map<String, Object>)kakaoAccount.get("profile");
        return OAuthAttributes.builder()
                .nickname((String)profile.get("nickname"))
                .loginId((String)kakaoAccount.get("email"))
                .image((String)profile.get("profile_image_url"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .nickname((String) response.get("name"))
                .loginId((String) response.get("email"))
                .image((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity(String registrationId) {
        return User.builder()
                .nickname(nickname)
                .loginId(loginId)
                .image(image)
                .loginType(registrationId)
                .dflag(false)
                .role(Role.USER)
                .build();
    }
}