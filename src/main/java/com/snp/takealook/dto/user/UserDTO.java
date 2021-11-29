package com.snp.takealook.dto.user;

import lombok.Builder;
import lombok.Data;

public class UserDTO {

    @Data
    public static class Check {
        private String nickname;
    }

    @Data
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

    @Data
    public static class Delete {
        private boolean dFlag;

        @Builder
        public Delete(boolean dFlag) {
            this.dFlag = dFlag;
        }
    }
}
