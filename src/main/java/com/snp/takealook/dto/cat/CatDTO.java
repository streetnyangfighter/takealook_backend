package com.snp.takealook.dto.cat;

import com.snp.takealook.domain.cat.Cat;
import com.snp.takealook.domain.cat.CatGroup;
import com.snp.takealook.domain.user.User;
import lombok.Builder;
import lombok.Getter;

public class CatDTO {

    @Getter
    public static class Create {
        private Long userId;
        private String name;
        private Byte neutered;
        private Byte status;

        @Builder
        public Create(Long userId, String name, Byte neutered, Byte status) {
            this.userId = userId;
            this.name = name;
            this.neutered = neutered;
            this.status = status;
        }

        public Cat toEntity(User user) {
            return Cat.builder()
                    .user(user)
                    .name(name)
                    .neutered(neutered)
                    .status(status)
                    .build();
        }
    }

    @Getter
    public static class Update {
        private String name;
        private Byte neutered;
        private Byte status;
    }

    @Getter
    public static class LocationList {
        private double latitude;
        private double longitude;
    }

}
