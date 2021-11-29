package com.snp.takealook.dto.user;


import com.snp.takealook.domain.user.User;
import com.snp.takealook.domain.user.UserLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserLocationDTO {

    @Getter
    @NoArgsConstructor
    public static class Create {
        private User user;
        private String sido;
        private String gugun;
        private String dong;

        @Builder
        public Create(User user, String sido, String gugun, String dong) {
            this.user = user;
            this.sido = sido;
            this.gugun = gugun;
            this.dong = dong;
        }

        public UserLocation toEntity() {
            return UserLocation.builder()
                    .user(user)
                    .sido(sido)
                    .gugun(gugun)
                    .dong(dong)
                    .build();
        }
    }

}
