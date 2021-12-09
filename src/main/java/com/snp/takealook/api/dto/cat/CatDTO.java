package com.snp.takealook.api.dto.cat;

import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.user.User;
import lombok.Builder;
import lombok.Getter;

public class CatDTO {

    @Getter
    public static class Create {
        private String name;
        private Byte gender;
        private Byte neutered;
        private Byte status;
        private Byte pattern;

        @Builder
        public Create(String name, Byte gender, Byte neutered, Byte status, Byte pattern) {
            this.name = name;
            this.gender = gender;
            this.neutered = neutered;
            this.status = status;
            this.pattern = pattern;
        }

        public Cat toEntity() {
            return Cat.builder()
                    .name(name)
                    .gender(gender)
                    .neutered(neutered)
                    .status(status)
                    .pattern(pattern)
                    .build();
        }
    }

    @Getter
    public static class Update {
        private String name;
        private Byte gender;
        private Byte neutered;
        private Byte status;
        private Byte pattern;
    }

    @Getter
    public static class LocationList {
        private double latitude;
        private double longitude;
    }

}
