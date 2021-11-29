package com.snp.takealook.dto.cat;

import com.snp.takealook.domain.cat.Cat;
import com.snp.takealook.domain.cat.CatCare;
import lombok.Builder;
import lombok.Getter;

public class CatCareDTO {

    @Getter
    public static class Create {
        private Long catId;
        private byte type;
        private String message;

        @Builder
        public Create(Long catId, byte type, String message) {
            this.catId = catId;
            this.type = type;
            this.message = message;
        }

        public CatCare toEntity(Cat cat) {
            return CatCare.builder()
                    .cat(cat)
                    .type(type)
                    .message(message)
                    .build();
        }
    }

    @Getter
    public static class Update {
        private byte type;
        private String message;
    }
}
