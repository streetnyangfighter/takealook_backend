package com.snp.takealook.api.service.cat;

import com.snp.takealook.api.domain.BaseTimeEntity;
import com.snp.takealook.api.domain.cat.Selection;
import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.cat.CatCare;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.cat.CatCareDTO;
import com.snp.takealook.api.repository.cat.SelectionRepository;
import com.snp.takealook.api.repository.cat.CatCareRepository;
import com.snp.takealook.api.repository.cat.CatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatCareService {

    private final CatCareRepository catCareRepository;
    private final CatRepository catRepository;
    private final SelectionRepository selectionRepository;

    @Transactional(rollbackFor = Exception.class)
    public Long save(Long userId, Long catId, CatCareDTO.Create dto) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));

        return catCareRepository.save(dto.toEntity(mySelection)).getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public Long update(Long userId, Long catId, Long catcareId, CatCareDTO.Update dto) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));
        CatCare catCare = catCareRepository.findById(catcareId).orElseThrow(() -> new IllegalArgumentException("CatCare with id: " + catcareId + " is not valid"));

        if (!Objects.equals(catCare.getSelection().getId(), mySelection.getId())) {
            throw new IllegalStateException("only can update my catcare");
        }
        return catCare.update(dto.getType(), dto.getMessage()).getSelection().getCat().getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long userId, Long catId, Long catcareId) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));
        CatCare catCare = catCareRepository.findById(catcareId).orElseThrow(() -> new IllegalArgumentException("CatCare with id: " + catcareId + " is not valid"));

        if (!Objects.equals(catCare.getSelection().getId(), mySelection.getId())) {
            throw new IllegalStateException("only can delete my catcare");
        }
        catCareRepository.delete(catCare);
    }

    @Transactional(readOnly = true)
    public List<ResponseDTO.CatCareListResponse> findByCatIdAndDate(Long catId, LocalDateTime dayStart, LocalDateTime dayEnd) {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));
        List<Selection> selectionList = cat.getSelectionList();

        List<CatCare> catCareList = new ArrayList<>();
        for (Selection selection : selectionList) {
            catCareList.addAll(catCareRepository.findCatCaresBySelectionAndCreatedAtBetween(selection, dayStart, dayEnd));
        }

        catCareList.sort(Comparator.comparing(BaseTimeEntity::getCreatedAt).reversed());

        return catCareList.stream()
                .map(ResponseDTO.CatCareListResponse::new)
                .collect(Collectors.toList());
    }

}
