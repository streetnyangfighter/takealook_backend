package com.snp.takealook.api.dto.cat;

import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.cat.CatStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CatStatusDTO {

    @Getter
    @NoArgsConstructor
    public static class Create {
        private Byte status;

        public CatStatus toEntity(Cat cat) {
            return CatStatus.builder()
                    .cat(cat)
                    .status(status)
                    .build();
        }
    }

}
