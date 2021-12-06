package com.snp.takealook.dto.cat;

import com.snp.takealook.domain.cat.Cat;
import com.snp.takealook.domain.cat.CatStatus;
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
