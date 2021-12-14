package com.snp.takealook.api.dto.user;

import com.snp.takealook.api.domain.user.Role;
import com.snp.takealook.api.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class UserDTO {

    @ToString
    @Getter
    public static class Login {
        String code;
        String provider;
    }

    @Builder
    @Getter
    public static class Register {
        String loginId;
        String nickname;
        String image;
        String loginType;

        public User toEntity() {
            return User.builder()
                    .loginId(loginId)
                    .nickname(nickname)
                    .image(image)
                    .loginType(loginType)
                    .role(Role.USER)
                    .build();
        }
    }

    @Builder
    @Getter
    @Setter
    public static class LoginInfo {
        Long id;
        String loginId;
        String nickname;
        String accessToken;
        String refreshToken;
    }

    @Getter
    public static class UserInfo {
        String nickname;
        String loginId;

        public User toEntity() {
            return User.builder()
                    .nickname(nickname)
                    .loginId(loginId)
                    .build();
        }
    }

    @Getter
    public static class Check {
        private String nickname;
    }

    @Getter
    public static class Update {
        private String nickname;
        private String phone;
        private String image;

        @Builder
        public Update(String nickname, String phone, String image) {
            this.nickname = nickname;
            this.phone = phone;
            this.image = image;
        }
    }

    @Getter
    public static class Delete {
        private Boolean dflag;

        @Builder
        public Delete(Boolean dflag) {
            this.dflag = dflag;
        }
    }

    @Getter
    public static class LocationList {
        private String sido;
        private String gugun;
        private String dong;
    }
}
