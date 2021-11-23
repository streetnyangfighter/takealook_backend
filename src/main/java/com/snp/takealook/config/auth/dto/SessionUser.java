package com.snp.takealook.config.auth.dto;

import com.snp.takealook.domain.user.User;
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
