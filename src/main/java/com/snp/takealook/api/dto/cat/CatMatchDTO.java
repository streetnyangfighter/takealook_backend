package com.snp.takealook.api.dto.cat;

import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.cat.CatMatch;
import lombok.Builder;
import lombok.Getter;

public class CatMatchDTO {

    @Getter
    public static class Match {
        private Long proposerCatId;
        private Long accepterCatId;

        @Builder
        public Match(Long proposerCatId, Long accepterCatId) {
            this.proposerCatId = proposerCatId;
            this.accepterCatId = accepterCatId;
        }

        public CatMatch toEntity(Cat proposer, Cat accepter) {
            return CatMatch.builder()
                    .proposer(proposer)
                    .accepter(accepter)
                    .build();
        }
    }
}
