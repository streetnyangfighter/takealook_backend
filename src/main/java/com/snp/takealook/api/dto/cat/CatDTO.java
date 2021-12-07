package com.snp.takealook.api.dto.cat;

import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.user.User;
import lombok.Builder;
import lombok.Getter;

public class CatDTO {

    @Getter
    public static class Create {
        private Long userId;
        private String name;
        private Byte gender;
        private Byte neutered;
        private Byte status;
        private String info;

        @Builder
        public Create(Long userId, String name, Byte gender, Byte neutered, Byte status, String info) {
            this.userId = userId;
            this.name = name;
            this.gender = gender;
            this.neutered = neutered;
            this.status = status;
            this.info = info;
        }

        public Cat toEntity(User user) {
            return Cat.builder()
                    .user(user)
                    .name(name)
                    .gender(gender)
                    .neutered(neutered)
                    .status(status)
                    .info(info)
                    .build();
        }
    }

    @Getter
    public static class Update {
        private String name;
        private Byte gender;
        private Byte neutered;
        private Byte status;
        private String info;
    }

    @Getter
    public static class LocationList {
        private double latitude;
        private double longitude;
    }

}
