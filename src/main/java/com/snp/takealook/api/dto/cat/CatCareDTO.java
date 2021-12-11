package com.snp.takealook.api.dto.cat;

import com.snp.takealook.api.domain.cat.Selection;
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

        public CatCare toEntity(Selection selection) {
            return CatCare.builder()
                    .selection(selection)
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
