package com.snp.takealook.service.cat;

import com.snp.takealook.domain.cat.Cat;
import com.snp.takealook.dto.cat.CatStatusDTO;
import com.snp.takealook.repository.cat.CatRepository;
import com.snp.takealook.repository.cat.CatStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
