package com.snp.takealook.api.dto.oauth;

import com.snp.takealook.api.domain.user.ProviderType;

import java.util.Map;

public class KakaoUserInfo extends OAuth2UserInfo {

    public KakaoUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    Map<String,Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
    Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

    @Override
    public String getUsername() {
        return "Kakao_" + (String)kakaoAccount.get("email");
    }

    @Override
    public String getEmail() {
        return (String)kakaoAccount.get("email");
    }

    @Override
    public String getNickname() {
        return (String)profile.get("nickname");
    }

    @Override
    public String getImage() {
        return (String) profile.get("nickname");
    }
}
