//package com.snp.takealook.dto.cat;
//
//import com.snp.takealook.domain.cat.Cat;
//import com.snp.takealook.domain.cat.CatImage;
//import lombok.Builder;
//import lombok.Data;
//import lombok.Getter;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//public class CatImageDTO {
//
//    @Getter
//    public static class Create {
//        private String origFileName;
//        private String filePath;
//        private Long fileSize;
//
//        @Builder
//        public Create(String origFileName, String filePath, Long fileSize) {
//            this.origFileName = origFileName;
//            this.filePath = filePath;
//            this.fileSize = fileSize;
//        }
//
//        public CatImage toEntity() {
//            return CatImage.builder()
//                    .origFileName(origFileName)
//                    .filePath(filePath)
//                    .fileSize(fileSize)
//                    .build();
//        }
//    }
//}
