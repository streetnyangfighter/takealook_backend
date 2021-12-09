package com.snp.takealook.api.service.cat;

import com.snp.takealook.api.domain.Selection;
import com.snp.takealook.api.domain.cat.CatLocation;
import com.snp.takealook.api.dto.cat.CatDTO;
import com.snp.takealook.api.repository.SelectionRepository;
import com.snp.takealook.api.repository.cat.CatLocationRepository;
import com.snp.takealook.api.repository.cat.CatRepository;
import com.snp.takealook.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatLocationService {

    private final CatLocationRepository catLocationRepository;
    private final SelectionRepository selectionRepository;

    @Transactional
    public Long saveAll(Long selectionId, CatDTO.LocationList[] dtoList) {
        Selection selection = selectionRepository.findById(selectionId).orElseThrow(() -> new IllegalArgumentException("Selection with id: " + selectionId + " is not valid"));

        List<CatLocation> list = Arrays.stream(dtoList)
                .map(v -> CatLocation.builder()
                        .selection(selection)
                        .latitude(v.getLatitude())
                        .longitude(v.getLongitude())
                        .build())
                .collect(Collectors.toList());

        catLocationRepository.saveAll(list);

        return selectionId;
    }

    // CatLocation update로 할 지, 누적 save 할 지 보류
    @Transactional
    public Long update(Long userId, Long catId, CatDTO.LocationList[] dtoList) {
        Selection selection = selectionRepository.findSelectionByUser_IdAndCat_IdAndDflagFalse(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));

        catLocationRepository.deleteAll(selection.getCatLocationList());

        List<CatLocation> list = Arrays.stream(dtoList)
                .map(v -> CatLocation.builder()
                        .selection(selection)
                        .latitude(v.getLatitude())
                        .longitude(v.getLongitude())
                        .build())
                .collect(Collectors.toList());

        return selection.updateCatLocationList(list).getId();
    }

    // 고양이별 발견 지역 전체 조회
}
