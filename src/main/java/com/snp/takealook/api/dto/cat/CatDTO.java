package com.snp.takealook.api.dto.cat;

import com.snp.takealook.api.domain.cat.Cat;
import lombok.AllArgsConstructor;
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

        public Cat toEntity(String image, CatPoint catPoints) {
            return Cat.builder()
                    .name(name)
                    .gender(gender)
                    .neutered(neutered)
                    .status(status)
                    .pattern(pattern)
                    .image(image)
                    .leftEarX(catPoints.getLeftEarX())
                    .leftEarY(catPoints.getLeftEarY())
                    .rightEarX(catPoints.getRightEarX())
                    .rightEarY(catPoints.getRightEarY())
                    .leftEyeX(catPoints.getLeftEyeX())
                    .leftEyeY(catPoints.getLeftEyeY())
                    .rightEyeX(catPoints.getRightEyeX())
                    .rightEyeY(catPoints.getRightEyeY())
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
    public static class Location {
        private Double latitude;
        private Double longitude;
    }

    @Getter
    @AllArgsConstructor
    public static class CatPoint {
        private Double leftEarX;
        private Double leftEarY;
        private Double rightEarX;
        private Double rightEarY;
        private Double leftEyeX;
        private Double leftEyeY;
        private Double rightEyeX;
        private Double rightEyeY;
    }

}
