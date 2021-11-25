package com.snp.takealook.dto;

import com.snp.takealook.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
