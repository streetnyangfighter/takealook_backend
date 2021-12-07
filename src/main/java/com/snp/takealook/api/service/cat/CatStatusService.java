package com.snp.takealook.api.service.cat;

import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.dto.cat.CatStatusDTO;
import com.snp.takealook.api.repository.cat.CatRepository;
import com.snp.takealook.api.repository.cat.CatStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CatStatusService {
    private final CatStatusRepository catStatusRepository;
    private final CatRepository catRepository;

    @Transactional
    public Long save(Long catId, CatStatusDTO.Create dto) {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));

        return catStatusRepository.save(dto.toEntity(cat)).getId();
    }

}
