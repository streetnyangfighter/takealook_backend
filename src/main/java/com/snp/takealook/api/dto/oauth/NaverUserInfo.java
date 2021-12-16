package com.snp.takealook.api.dto.oauth;

import java.util.Map;

public class NaverUserInfo extends OAuth2UserInfo {

    public NaverUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    Map<String, Object> response = (Map<String, Object>) attributes.get("response");

    @Override
    public String getUsername() {
        return "Naver_" + (String) response.get("email");
    }

    @Override
    public String getEmail() {
        return (String) response.get("email");
    }

    @Override
    public String getNickname() {
        return (String) response.get("name");
    }

    @Override
    public String getImage() {
        return (String) response.get("profile_image");
    }

}
