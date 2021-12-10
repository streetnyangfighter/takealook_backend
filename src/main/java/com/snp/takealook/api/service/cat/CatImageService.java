package com.snp.takealook.api.service.cat;

import com.snp.takealook.api.domain.Selection;
import com.snp.takealook.api.domain.cat.CatImage;
import com.snp.takealook.api.repository.SelectionRepository;
import com.snp.takealook.api.repository.cat.CatImageRepository;
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
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CatImageService {

    private final CatImageRepository catImageRepository;
    private final SelectionRepository selectionRepository;

    @Transactional
    public Long save(Long selectionId, List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {
        Selection selection = selectionRepository.findById(selectionId).orElseThrow(() -> new IllegalArgumentException("Selection with id: " + selectionId + " is not valid"));

        if (files.size() != 0) {
            for (MultipartFile file : files) {
                String originalFileName = file.getOriginalFilename();
                String contentType = file.getContentType();
                Long fileSize = file.getSize();
                String originalFileExtension;
                String savePath = System.getProperty("user.dir") + "/images/catImages/" + selection.getCat().getId();

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
                        .selection(selection)
                        .originFileName(originalFileName)
                        .fileName(fileName)
                        .contentType(originalFileExtension)
                        .filePath(filePath)
                        .fileSize(fileSize)
                        .build();

                catImageRepository.save(catImage);
            }
        }

        return selectionId;
    }

    @Transactional
    public Long update(Long userId, Long catId, List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {
        Selection selection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));

        List<CatImage> catImageList = selection.getCatImageList();
        for (CatImage catImage : catImageList) {
            File file = new File(catImage.getFilePath());

            if (file.exists()) {
                file.delete();
            }else {
                System.out.println("file not exists");
            }
        }

        catImageRepository.deleteAll(selection.getCatImageList());
        selection.getCatImageList().clear();

        return save(selection.getId(), files);
    }

    @Transactional(readOnly = true)
    public List<File> findImagesByCatId(Long userId, Long catId) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId)
                .orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));

        List<Selection> selectionList = selectionRepository.findSelectionsByCat(mySelection.getCat());
        List<File> fileList = new ArrayList<>();
        for (Selection selection : selectionList) {
            List<CatImage> imageList = selection.getCatImageList();
            for (CatImage catImage : imageList) {
                File file = new File(catImage.getFilePath());
                fileList.add(file);
            }
        }

        return fileList;
    }
}
