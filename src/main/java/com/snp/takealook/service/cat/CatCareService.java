package com.snp.takealook.service.cat;

import com.snp.takealook.domain.cat.Cat;
import com.snp.takealook.domain.cat.CatCare;
import com.snp.takealook.domain.cat.CatGroup;
import com.snp.takealook.dto.ResponseDTO;
import com.snp.takealook.dto.cat.CatCareDTO;
import com.snp.takealook.repository.cat.CatCareRepository;
import com.snp.takealook.repository.cat.CatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatCareService {

    private final CatCareRepository catCareRepository;
    private final CatRepository catRepository;

    @Transactional
    public Long save(CatCareDTO.Create dto) {
        Cat cat = catRepository.findById(dto.getCatId()).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + dto.getCatId() + " is not valid"));

        return catCareRepository.save(dto.toEntity(cat)).getId();
    }

    @Transactional
    public Long update(Long id, CatCareDTO.Update dto) {
        CatCare catCare = catCareRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("CatCare with id: " + id + " is not valid"));

        catCare.update(dto.getType(), dto.getMessage());

        return id;
    }

    @Transactional
    public Long delete(Long id) {
        CatCare catCare = catCareRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("CatCare with id: " + id + " is not valid"));

        catCareRepository.delete(catCare);

        return id;
    }

    @Transactional
    public List<ResponseDTO.CatCareListResponse> findAllByCatId(Long catId) {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));
        List<Cat> sameGroupCatList = cat.getCatGroup().getCatList();
        List<CatCare> catCareList = new ArrayList<>();

        for (Cat sameCat : sameGroupCatList) {
            catCareList.addAll(sameCat.getCatCareList());
        }

        return catCareList.stream()
                .map(ResponseDTO.CatCareListResponse::new)
                .collect(Collectors.toList());
    }
}
