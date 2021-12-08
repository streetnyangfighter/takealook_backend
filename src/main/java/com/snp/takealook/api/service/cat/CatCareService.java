package com.snp.takealook.api.service.cat;

import com.snp.takealook.api.domain.BaseTimeEntity;
import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.cat.CatCare;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.cat.CatCareDTO;
import com.snp.takealook.api.repository.cat.CatCareRepository;
import com.snp.takealook.api.repository.cat.CatRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.registry.LocateRegistry;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatCareService {

    private final CatCareRepository catCareRepository;
    private final CatRepository catRepository;

    @Transactional
    public Long save(Long catId, CatCareDTO.Create dto) {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));

        return catCareRepository.save(dto.toEntity(cat)).getId();
    }

    @Transactional
    public Long update(Long catcareId, CatCareDTO.Update dto) {
        CatCare catCare = catCareRepository.findById(catcareId).orElseThrow(() -> new IllegalArgumentException("CatCare with id: " + catcareId + " is not valid"));

        catCare.update(dto.getType(), dto.getMessage());

        return catcareId;
    }

    @Transactional
    public Long delete(Long catcareId) {
        CatCare catCare = catCareRepository.findById(catcareId).orElseThrow(() -> new IllegalArgumentException("CatCare with id: " + catcareId + " is not valid"));

        catCareRepository.delete(catCare);

        return catcareId;
    }

    @Transactional
    public List<ResponseDTO.CatCareListResponse> findAllByCatId(Long catId) {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));
        List<CatCare> catCareList = new ArrayList<>();

        try {
            List<Cat> sameGroupCatList = cat.getCatGroup().getCatList();
            for (Cat sameCat : sameGroupCatList) {
                catCareList.addAll(sameCat.getCatCareList());
            }
        } catch (NullPointerException e) {
            catCareList.addAll(cat.getCatCareList());
        }

        catCareList.sort(Comparator.comparing(BaseTimeEntity::getModifiedAt));

        return catCareList.stream()
                .map(ResponseDTO.CatCareListResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ResponseDTO.CatCareListResponse> findByCatIdAndDate(Long catId, LocalDateTime dayStart, LocalDateTime dayEnd) {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));
        List<CatCare> catCareList = new ArrayList<>();

        try {
            List<Cat> sameGroupCatList = cat.getCatGroup().getCatList();
            for (Cat sameCat : sameGroupCatList) {
                catCareList.addAll(catCareRepository.findCatCaresByCreatedAtIsBetweenAndCat_Id(dayStart, dayEnd, sameCat.getId()));
            }
        } catch (NullPointerException e) {
            catCareList.addAll(catCareRepository.findCatCaresByCreatedAtIsBetweenAndCat_Id(dayStart, dayEnd, catId));
        }

        catCareList.sort(Comparator.comparing(BaseTimeEntity::getCreatedAt));

        return catCareList.stream()
                .map(ResponseDTO.CatCareListResponse::new)
                .collect(Collectors.toList());
    }

}
