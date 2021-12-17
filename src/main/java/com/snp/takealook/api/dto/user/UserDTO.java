package com.snp.takealook.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDTO {

    @Getter
    public static class Login {
        String token;
        String provider;
    }

    @Getter
    public static class Check {
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    public static class Update {
        private String nickname;
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
