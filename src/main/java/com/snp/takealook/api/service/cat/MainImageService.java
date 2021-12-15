package com.snp.takealook.api.service.cat;

import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.cat.MainImage;
import com.snp.takealook.api.repository.cat.CatRepository;
import com.snp.takealook.api.repository.cat.MainImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class MainImageService {

    private final MainImageRepository mainImageRepository;
    private final CatRepository catRepository;

    @Transactional
    public Long save(Long catId, MultipartFile file) throws IOException {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));

        if (file != null) {
            String originalFileName = file.getOriginalFilename();
            String contentType = file.getContentType();
            Long fileSize = file.getSize();
            String originalFileExtension;
            String savePath = System.getProperty("user.dir") + "/images/catImages/" + cat.getId();

            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    System.out.println("file: was not successful");
                    System.out.println(e.getMessage());
                }
            }

            if (ObjectUtils.isEmpty(contentType)) {
                throw new IllegalArgumentException("이미지 형식이 존재하지 않습니다.");
            } else {
                if (contentType.contains("image/jpeg")) {
                    originalFileExtension = ".jpg";
                } else if (contentType.contains("image/png")) {
                    originalFileExtension = ".png";
                } else {
                    throw new IllegalArgumentException("형식에 맞지 않는 이미지 파일(jpg, png 외)입니다.");
                }
            }

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String current_date = now.format(dateTimeFormatter);

            String fileName = "main-" + current_date;
            String filePath = savePath + "/" + fileName;

            file.transferTo(new File(filePath));

            MainImage mainImage = MainImage.builder()
                    .cat(cat)
                    .originFileName(originalFileName)
                    .fileName(fileName)
                    .contentType(originalFileExtension)
                    .fileSize(fileSize)
                    .filePath(filePath)
                    .build();

            mainImageRepository.save(mainImage);
            cat.setMainImg(mainImage);
        }

        return cat.getId();
    }

    @Transactional
    public Long update(Long catId, MultipartFile file) throws IOException {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));

        File exist = new File(cat.getMainImage().getFilePath());

        if (exist.exists()) {
            exist.delete();
        } else {
            System.out.println("file not exists");
        }

        mainImageRepository.delete(cat.getMainImage());

        return save(catId, file);
    }

}
