package com.snp.takealook.api.service.cat;

import com.snp.takealook.api.domain.BaseTimeEntity;
import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.cat.CatLocation;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.cat.CatDTO;
import com.snp.takealook.api.repository.cat.CatLocationRepository;
import com.snp.takealook.api.repository.cat.CatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatLocationService {

    private final CatLocationRepository catLocationRepository;
    private final CatRepository catRepository;

    @Transactional
    public Long save(Long catId, CatDTO.LocationList[] dtoList) {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));

        List<CatLocation> list = Arrays.stream(dtoList)
                .map(v -> CatLocation.builder()
                        .cat(cat)
                        .latitude(v.getLatitude())
                        .longitude(v.getLongitude())
                        .build())
                .collect(Collectors.toList());

        return cat.updateLocations(list).getId();
    }

    @Transactional
    public Long update(Long catId, CatDTO.LocationList[] dtoList) {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));

        catLocationRepository.deleteAll(cat.getCatLocationList());

        List<CatLocation> list = Arrays.stream(dtoList)
                .map(v -> CatLocation.builder()
                        .cat(cat)
                        .latitude(v.getLatitude())
                        .longitude(v.getLongitude())
                        .build())
                .collect(Collectors.toList());

        return cat.updateLocations(list).getId();
    }

    @Transactional
    public List<ResponseDTO.CatLocationListResponse> findAllByCatId(Long catId) {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));
        List<CatLocation> catLocationList = new ArrayList<>();

        try { // 매칭이 되어있는 상태
            List<Cat> sameGroupCatList = cat.getCatGroup().getCatList();
            for (Cat sameCat : sameGroupCatList) {
                catLocationList.addAll(sameCat.getCatLocationList());
            }
        } catch (NullPointerException e) { // 매칭 없이 단독 고양이 상태
            catLocationList.addAll(cat.getCatLocationList());
        }

        catLocationList.sort(Comparator.comparing(BaseTimeEntity::getCreatedAt));

        return catLocationList.stream()
                .map(ResponseDTO.CatLocationListResponse::new)
                .collect(Collectors.toList());
    }
}
