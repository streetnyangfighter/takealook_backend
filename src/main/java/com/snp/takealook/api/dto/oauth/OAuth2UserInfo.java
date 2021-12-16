package com.snp.takealook.api.dto.oauth;

import com.snp.takealook.api.domain.user.ProviderType;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getUsername();

    public abstract String getEmail();

    public abstract String getNickname();

    public abstract String getImage();
}
