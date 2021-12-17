package com.snp.takealook.api.service.cat;

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
                           CatDTO.LocationList[] catLocList,
                           MultipartFile file,
                           List<MultipartFile> files) throws IOException {
        String mainImage = s3Uploader.upload(file, "static");
        Long catId = catService.save(catInfo, mainImage);
        Long selectionId = selectionService.save(userId, catId);
        catLocationService.saveAll(selectionId, catLocList);
        if (files.size() != 0) {
            for (MultipartFile m : files) {
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
                          CatDTO.LocationList[] catLocList,
                          MultipartFile file,
                          List<MultipartFile> files) throws IOException {
        String mainImage = s3Uploader.upload(file, "static");
        catService.update(userId, catId, catInfo, mainImage);
        catLocationService.update(userId, catId, catLocList);
        List<String> pathList = new ArrayList<>();
        for (MultipartFile m : files) {
            String path = s3Uploader.upload(m, "static");
            pathList.add(path);
        }
        catImageService.update(userId, catId, pathList);
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
                                          MultipartFile file) throws IOException {
        String mainImage = s3Uploader.upload(file, "static");
        Long newCatId = catService.save(catInfo, mainImage);
        selectionService.update(userId, catId, newCatId);
        notificationService.catSave(userId, catId, (byte) 5);

        return newCatId;
    }
}
