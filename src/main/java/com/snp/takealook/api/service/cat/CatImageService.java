package com.snp.takealook.api.service.cat;

import com.snp.takealook.api.domain.cat.Selection;
import com.snp.takealook.api.domain.cat.CatImage;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.repository.cat.SelectionRepository;
import com.snp.takealook.api.repository.cat.CatImageRepository;
import com.snp.takealook.api.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatImageService {

    private final CatImageRepository catImageRepository;
    private final SelectionRepository selectionRepository;
    private final S3Uploader s3Uploader;

    @Transactional(rollbackFor = Exception.class)
    public Long save(Long selectionId, String path) {
        Selection selection = selectionRepository.findById(selectionId).orElseThrow(() -> new IllegalArgumentException("Selection with id: " + selectionId + " is not valid"));

        return catImageRepository.save(CatImage.builder()
                .selection(selection)
                .path(path)
                .build()).getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public Long update(Long userId, Long catId, List<String> pathList) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));

        for (CatImage catImage : mySelection.getCatImageList()) {
            s3Uploader.fileDelete(catImage.getPath());
        }

        catImageRepository.deleteAll(mySelection.getCatImageList());
        mySelection.getCatImageList().clear();

        for (String path : pathList) {
            save(mySelection.getId(), path);
        }

        return mySelection.getId();
    }

    @Transactional(readOnly = true)
    public List<ResponseDTO.CatImageListResponse> findImagesByCatId(Long userId, Long catId) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId)
                .orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));

        List<CatImage> catImageList = new ArrayList<>();
        List<Selection> selectionList = mySelection.getCat().getSelectionList();
        for (Selection selection : selectionList) {
            catImageList.addAll(selection.getCatImageList());
        }

        return catImageList.stream()
                .map(ResponseDTO.CatImageListResponse::new)
                .collect(Collectors.toList());
    }

}
