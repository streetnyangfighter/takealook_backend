package com.snp.takealook.config.auth.dto;

import com.snp.takealook.domain.user.Role;
import com.snp.takealook.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String nickname;
    private String loginId;
    private byte[] image;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String nickname, String loginId, byte[] image) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.nickname = nickname;
        this.loginId = loginId;
        this.image = image;
    }

    // OAuth2User에서 반환하는 Map 사용자 정보를 받아 각 attribute로 변환
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nickname((String) attributes.get("nickname"))
                .loginId((String) attributes.get("loginId"))
                .image((byte[]) attributes.get("image"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .loginId(loginId)
                .image(image)
                .dFlag(false)
                .role(Role.GUEST)
                .build();
    }
}
