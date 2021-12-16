package com.snp.takealook.api.dto.oauth;

import java.util.Map;

public class GoogleUserInfo extends OAuth2UserInfo{
    public GoogleUserInfo(Map<String, Object> attributes) { super(attributes); }

    @Override
    public String getUsername() {
        return "Google_" + (String) attributes.get("email");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getNickname() {
        return (String) attributes.get("name");
    }

    @Override
    public String getImage() {
        return (String) attributes.get("picture");
    }
}
