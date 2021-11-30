package com.snp.takealook.dto.user;

import lombok.Builder;
import lombok.Getter;

public class UserDTO {

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
        private boolean dFlag;

        @Builder
        public Delete(boolean dFlag) {
            this.dFlag = dFlag;
        }
    }

    @Getter
    public static class LocationList {
        private String sido;
        private String gugun;
        private String dong;
    }
}
