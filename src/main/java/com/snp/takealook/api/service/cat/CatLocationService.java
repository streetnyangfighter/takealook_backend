package com.snp.takealook.api.service.cat;

import com.snp.takealook.api.domain.cat.*;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.cat.CatDTO;
import com.snp.takealook.api.repository.cat.CatLocationRepository;
import com.snp.takealook.api.repository.cat.SelectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatLocationService {

    private final CatLocationRepository catLocationRepository;
    private final SelectionRepository selectionRepository;
    private final EntityManager em;

    @Transactional(rollbackFor = Exception.class)
    public Long saveAll(Long userId, Long catId, CatDTO.Location[] dtoList) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));

        List<CatLocation> list = Arrays.stream(dtoList)
                .map(v -> CatLocation.builder()
                        .selection(mySelection)
                        .latitude(v.getLatitude())
                        .longitude(v.getLongitude())
                        .build())
                .collect(Collectors.toList());

        catLocationRepository.saveAll(list);

        return mySelection.getId();
    }

//    @Transactional(rollbackFor = Exception.class)
//    public Long update(Long userId, Long catId, CatDTO.LocationList[] dtoList) {
//        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));
//
//        catLocationRepository.deleteAll(mySelection.getCatLocationList());
//
//        List<CatLocation> list = Arrays.stream(dtoList)
//                .map(v -> CatLocation.builder()
//                        .selection(mySelection)
//                        .latitude(v.getLatitude())
//                        .longitude(v.getLongitude())
//                        .build())
//                .collect(Collectors.toList());
//
//        return mySelection.updateCatLocationList(list).getId();
//    }

    @Transactional(readOnly = true)
    public List<ResponseDTO.CatLocationResponse> findLocationsByCatId(Long userId, Long catId) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId)
                .orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));

        List<Selection> selectionList = mySelection.getCat().getSelectionList();
        List<CatLocation> catLocationList = new ArrayList<>();
        for (Selection selection : selectionList) {
            catLocationList.addAll(selection.getCatLocationList());
        }

//        catLocationList.sort(Comparator.comparing(BaseTimeEntity::getCreatedAt));

        return catLocationList.stream()
                .map(ResponseDTO.CatLocationResponse::new)
                .collect(Collectors.toList());
    }

}
