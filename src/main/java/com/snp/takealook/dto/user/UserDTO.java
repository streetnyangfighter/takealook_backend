package com.snp.takealook.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

public class UserDTO {

    @Data
    public static class InitialUpdate {
        private String nickname;
        private String phone;
        private String image;

        @Builder
        public InitialUpdate(String nickname, String phone, String image) {
            this.nickname = nickname;
            this.phone = phone;
            this.image = image;
        }
    }

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
