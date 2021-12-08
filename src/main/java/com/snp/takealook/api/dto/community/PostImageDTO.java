package com.snp.takealook.api.dto.community;

import lombok.Builder;
import lombok.Getter;

public class PostImageDTO {

    @Getter
    public static class Create {
        private String origFileName;
        private String filePath;
        private Long fileSize;

        @Builder
        public Create(String origFileName, String filePath, Long fileSize) {
            this.origFileName = origFileName;
            this.filePath = filePath;
            this.fileSize = fileSize;
        }
    }
}
