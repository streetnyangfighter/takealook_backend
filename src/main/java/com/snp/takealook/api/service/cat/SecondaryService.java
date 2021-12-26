package com.snp.takealook.api.service.cat;

import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.dto.cat.CatDTO;
import com.snp.takealook.api.service.S3Uploader;
import com.snp.takealook.api.service.user.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SecondaryService {

    private final S3Uploader s3Uploader;
    private final CatService catService;
    private final SelectionService selectionService;
    private final CatLocationService catLocationService;
    private final CatImageService catImageService;
    private final NotificationService notificationService;

    @Transactional(rollbackFor = Exception.class)
    public Long saveNewCat(Long userId,
                           CatDTO.Create catInfo,
                           CatDTO.CatPoint catPoints,
                           CatDTO.Location[] catLocList,
                           MultipartFile file,
                           Optional<List<MultipartFile>> files) throws IOException {

        String mainImage = s3Uploader.upload(file, "static");
        Long catId = catService.save(catInfo, catPoints, mainImage);
        Long selectionId = selectionService.save(userId, catId);
        catLocationService.saveAll(userId, catId, catLocList);

        if (files.isPresent()) {
            for (MultipartFile m : files.get()) {
                String path = s3Uploader.upload(m, "static");
                catImageService.save(selectionId, path);
            }
        }

        return catId;
    }

    @Transactional(rollbackFor = Exception.class)
    public Long updateCat(Long userId,
                          Long catId,
                          CatDTO.Update catInfo,
                          CatDTO.Location[] catLocList,
                          Optional<MultipartFile> file,
                          Optional<CatDTO.CatPoint> catPoints,
                          Optional<String[]> deletedImgUrl,
                          Optional<List<MultipartFile>> files) throws IOException {

        catService.updateInfo(userId, catId, catInfo);

        if (file.isPresent() && catPoints.isPresent()) {
            String mainImage = s3Uploader.upload(file.get(), "static");
            catService.updateImage(userId, catId, mainImage);
            catService.updateCatPoints(userId, catId, catPoints.get());
        }

        catLocationService.saveAll(userId, catId, catLocList);

        if (files.isPresent()) {
            List<String> pathList = new ArrayList<>();
            for (MultipartFile m : files.get()) {
                String path = s3Uploader.upload(m, "static");
                pathList.add(path);
            }
            catImageService.update(userId, catId, deletedImgUrl, Optional.of(pathList));
        }

        if (!files.isPresent() && deletedImgUrl.isPresent()) {
            catImageService.update(userId, catId, deletedImgUrl, java.util.Optional.empty());
        }

        notificationService.catSave(userId, catId, (byte) 1);

        return catId;
    }

    @Transactional(rollbackFor = Exception.class)
    public Long updateSelectionWithExistCat(Long userId, Long catId, Long newCatId) {
        selectionService.update(userId, catId, newCatId);
        notificationService.catSave(userId, catId, (byte) 3);
        notificationService.catSave(userId, newCatId, (byte) 2);
        catService.delete(catId);

        return newCatId;
    }

    @Transactional(rollbackFor = Exception.class)
    public Long updateSelectionWithNewCat(Long userId,
                                          Long catId,
                                          CatDTO.Create catInfo,
                                          CatDTO.CatPoint catPoints,
                                          MultipartFile file) throws IOException {
        String mainImage = s3Uploader.upload(file, "static");
        Long newCatId = catService.save(catInfo, catPoints, mainImage);
        selectionService.update(userId, catId, newCatId);
        notificationService.catSave(userId, catId, (byte) 5);

        return newCatId;
    }
}
