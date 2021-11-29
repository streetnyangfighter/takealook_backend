package com.snp.takealook.dto.cat;

import com.snp.takealook.domain.cat.Cat;
import com.snp.takealook.domain.cat.CatGroup;
import com.snp.takealook.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public class CatDTO {

    @Getter
    public static class Create {
        private Long userId;
        private String name;
        private byte neutered;
        private byte status;

        @Builder
        public Create(Long userId, String name, byte neutered, byte status) {
            this.userId = userId;
            this.name = name;
            this.neutered = neutered;
            this.status = status;
        }

        public Cat toEntity(User user, CatGroup catGroup) {
            return Cat.builder()
                    .user(user)
                    .catGroup(catGroup)
                    .name(name)
                    .neutered(neutered)
                    .status(status)
                    .build();
        }
    }

    @Getter
    public static class Update {
        private String name;
        private byte neutered;
        private byte status;
    }

    @Getter
    public static class LocationList {
        private double latitude;
        private double longitude;
    }

}
