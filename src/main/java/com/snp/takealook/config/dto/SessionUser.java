package com.snp.takealook.config.dto;

import com.snp.takealook.api.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String loginId;
    private String nickname;
    private String image;

    public SessionUser(User user) {
        this.loginId = user.getLoginId();
        this.nickname = user.getNickname();
        this.image = user.getImage();
    }
}