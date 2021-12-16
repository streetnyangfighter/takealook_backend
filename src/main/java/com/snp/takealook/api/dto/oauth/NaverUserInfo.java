package com.snp.takealook.api.dto.oauth;

import java.util.Map;

public class NaverUserInfo extends OAuth2UserInfo{

    public NaverUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getNickname() {
        return null;
    }

    @Override
    public String getImage() {
        return null;
    }
}
