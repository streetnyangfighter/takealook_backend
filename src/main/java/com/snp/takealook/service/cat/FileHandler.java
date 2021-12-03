//package com.snp.takealook.service.cat;
//
//import com.snp.takealook.domain.cat.CatImage;
//import com.snp.takealook.dto.cat.CatImageDTO;
//import com.snp.takealook.service.cat.CatImageService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.ObjectUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class FileHandler {
//
//    public List<CatImage> parseFileInfo(List<MultipartFile> multipartFiles) throws IOException {
//        List<CatImage> fileList = new ArrayList<>();
//
//        if (!CollectionUtils.isEmpty(multipartFiles)) {
//            LocalDateTime now = LocalDateTime.now();
//            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//            String current_date = now.format(dateTimeFormatter);
//
//            String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;
//
//            String path = "images" + File.separator + current_date;
//            System.out.println("***" + path);
//            File file = new File(path);
//
//            if (!file.exists()) {
//                boolean wasSuccessful = file.mkdirs();
//
//                if (!wasSuccessful) {
//                    System.out.println("file: was not successful");
//                }
//            }
//
//            for (MultipartFile multipartFile : multipartFiles) {
//                String originalFileExtension;
//                String contentType = multipartFile.getContentType();
//
//                if (ObjectUtils.isEmpty(contentType)) {
//                    break;
//                } else {
//                    if (contentType.contains("image/jpeg")) {
//                        originalFileExtension = ".jpg";
//                    } else if (contentType.contains("image/png")) {
//                        originalFileExtension = ".png";
//                    } else {
//                        break;
//                    }
//                }
//
//                String new_file_name = System.nanoTime() + originalFileExtension;
//
//                CatImageDTO.Create dto = CatImageDTO.Create.builder()
//                        .origFileName(multipartFile.getOriginalFilename())
//                        .filePath(path + File.separator + new_file_name)
//                        .fileSize(multipartFile.getSize())
//                        .build();
//
////                CatImage catImage = new CatImage(
////                        dto.getOrigFileName(),
////                        dto.getFilePath(),
////                        dto.getFileSize()
////                );
////
////                fileList.add(catImage);
////
////                file = new File(absolutePath + path + File.separator + new_file_name);
////                multipartFile.transferTo(file);
////
////                file.setWritable(true);
////                file.setReadable(true);
//            }
//        }
//        return fileList;
//    }
//}
