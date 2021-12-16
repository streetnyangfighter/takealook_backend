package com.snp.takealook.api.dto.user;

import com.snp.takealook.api.domain.user.ProviderType;
import com.snp.takealook.api.domain.user.Role;
import com.snp.takealook.api.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class UserDTO {

    @Getter
    public static class Login {
        String token;
        String provider;
    }

//    @Builder
//    @Getter
//    public static class Register {
//        String loginId;
//        String nickname;
//        String image;
//        ProviderType providerType;
//
//        public User toEntity() {
//            return User.builder()
//                    .loginId(loginId)
//                    .nickname(nickname)
//                    .image(image)
//                    .providerType(providerType)
//                    .role(Role.USER)
//                    .build();
//        }
//    }

    //ResponseDTO로 빼기
//    @Builder
//    @Getter
//    @Setter
//    public static class LoginInfo {
//        Long id;
//        String loginId;
//        String nickname;
//        String accessToken;
//        String refreshToken;
//    }
//
//    @Getter
//    public static class UserInfo {
//        String nickname;
//        String loginId;
//
//        public User toEntity() {
//            return User.builder()
//                    .nickname(nickname)
//                    .loginId(loginId)
//                    .build();
//        }
//    }

    @Getter
    public static class Check {
        private String nickname;
    }

    @Getter
    public static class Update {
        private String nickname;
        private String image;

        @Builder
        public Update(String nickname, String image) {
            this.nickname = nickname;
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
