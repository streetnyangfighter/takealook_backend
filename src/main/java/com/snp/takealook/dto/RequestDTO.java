package com.snp.takealook.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class RequestDTO {

    @Data
    public static class UserUpdate {
        private String nickname;
        private String phone;
        private String image;

        @Builder
        public UserUpdate(String nickname, String phone, String image) {
            this.nickname = nickname;
            this.phone = phone;
            this.image = image;
        }
    }
}
