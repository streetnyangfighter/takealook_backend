package com.snp.takealook.api.dto.cat;

import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.cat.CatCare;
import lombok.Builder;
import lombok.Getter;

public class CatCareDTO {

    @Getter
    public static class Create {
        private Byte type;
        private String message;

        @Builder
        public Create(Byte type, String message) {
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
        private Byte type;
        private String message;
    }
}
