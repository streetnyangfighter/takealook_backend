package com.snp.takealook.api.dto.oauth;

import java.util.Map;

public class KakaoUserInfo implements OauthUserInfo {
    private Map<String, Object> attributes; //oAuth2User.getAttributes()
    private Map<String, Object> kakaoAccount;
    private Map<String, Object> userInfo;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getLoginId() {
        kakaoAccount = (Map)attributes.get("kakao_account");
        return (String) kakaoAccount.get("email");
    }

    @Override
    public String getNickname() {
        kakaoAccount = (Map)attributes.get("kakao_account");
        userInfo = (Map) kakaoAccount.get("profile");
        return (String) userInfo.get("nickname");
    }

    @Override
    public String getImage() {
        kakaoAccount = (Map)attributes.get("kakao_account");
        userInfo = (Map) kakaoAccount.get("profile");
        return (String) userInfo.get("profile_image_url");
    }

    @Override
    public String getLoginType() {
        return "kakao";
    }

}
