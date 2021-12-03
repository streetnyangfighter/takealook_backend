package com.snp.takealook.service.cat;

import com.snp.takealook.domain.cat.Cat;
import com.snp.takealook.domain.cat.CatImage;
import com.snp.takealook.repository.cat.CatImageRepository;
import com.snp.takealook.repository.cat.CatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@Service
public class CatImageService {

    private final CatImageRepository catImageRepository;
    private final CatRepository catRepository;

    @Transactional
    public Long save(Long catId, MultipartFile[] files) throws IOException, NoSuchAlgorithmException {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));

        if (files.length != 0) {
            for (MultipartFile file : files) {
                String originalFileName = file.getOriginalFilename();
                String contentType = file.getContentType();
                Long fileSize = file.getSize();
                String savePath = System.getProperty("user.dir") + "/catImages";

                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                String filePath = savePath + "/" + originalFileName;
                file.transferTo(new File(filePath));

                CatImage catImage = CatImage.builder()
                        .cat(cat)
                        .originFileName(originalFileName)
                        .contentType(contentType)
                        .filePath(filePath)
                        .fileSize(fileSize)
                        .build();

                System.out.println(catImage.getCat().getName());
                catImageRepository.save(catImage);

            }
        }
        return cat.getId();
    }
}
