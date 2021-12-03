package com.snp.takealook.service.cat;

import com.snp.takealook.domain.BaseTimeEntity;
import com.snp.takealook.domain.cat.Cat;
import com.snp.takealook.domain.cat.CatLocation;
import com.snp.takealook.dto.ResponseDTO;
import com.snp.takealook.dto.cat.CatDTO;
import com.snp.takealook.repository.cat.CatLocationRepository;
import com.snp.takealook.repository.cat.CatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatLocationService {

    private final CatLocationRepository catLocationRepository;
    private final CatRepository catRepository;

    @Transactional
    public Long save(Long catId, List<CatDTO.LocationList> dtoList) {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));

        List<CatLocation> list = dtoList.stream()
                .map(v -> CatLocation.builder()
                        .cat(cat)
                        .latitude(v.getLatitude())
                        .longitude(v.getLongitude())
                        .build())
                .collect(Collectors.toList());

        return cat.updateLocations(list).getId();
    }

    @Transactional
    public Long update(Long catId, List<CatDTO.LocationList> dtoList) {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));

        catLocationRepository.deleteAll(cat.getCatLocationList());

        List<CatLocation> list = dtoList.stream()
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
        List<Cat> sameGroupCatList = cat.getCatGroup().getCatList();
        List<CatLocation> catLocationList = new ArrayList<>();

        for (Cat sameCat : sameGroupCatList) {
            catLocationList.addAll(sameCat.getCatLocationList());
        }

        catLocationList.sort(Comparator.comparing(BaseTimeEntity::getCreatedAt));

        return catLocationList.stream()
                .map(ResponseDTO.CatLocationListResponse::new)
                .collect(Collectors.toList());
    }
}
