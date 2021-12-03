package com.snp.takealook.service.cat;

import com.snp.takealook.domain.cat.Cat;
import com.snp.takealook.domain.cat.CatImage;
import com.snp.takealook.repository.cat.CatImageRepository;
import com.snp.takealook.repository.cat.CatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CatImageService {

    private final CatImageRepository catImageRepository;
    private final CatRepository catRepository;

    @Transactional
    public Long save(Long catId, List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));

        if (files.size() != 0) {
            for (MultipartFile file : files) {
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
                    break;
                } else {
                    if (contentType.contains("image/jpeg")) {
                        originalFileExtension = ".jpg";
                    } else if (contentType.contains("image/png")) {
                        originalFileExtension = ".png";
                    } else {
                        break;
                    }
                }

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                String current_date = now.format(dateTimeFormatter);

                String fileName = current_date + "-" + originalFileName;
                String filePath = savePath + "/" + fileName;

                file.transferTo(new File(filePath));

                CatImage catImage = CatImage.builder()
                        .cat(cat)
                        .originFileName(originalFileName)
                        .fileName(fileName)
                        .filePath(filePath)
                        .contentType(originalFileExtension)
                        .fileSize(fileSize)
                        .build();

                System.out.println(catImage.getCat().getName());
                catImageRepository.save(catImage);

            }
        }
        return cat.getId();
    }

    @Transactional
    public Long update(Long catId, List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));
        List<CatImage> catImageList = cat.getCatImageList();

        for (CatImage catImage : catImageList) {
            File file = new File(catImage.getFilePath());

            if (file.exists()) {
                file.delete();
            }else {
                System.out.println("file not exists");
            }
        }

        catImageRepository.deleteAll(cat.getCatImageList());
        cat.getCatImageList().removeAll(catImageList);

        return save(catId, files);
    }

}
