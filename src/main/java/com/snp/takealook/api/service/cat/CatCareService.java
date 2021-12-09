package com.snp.takealook.api.service.cat;

import com.snp.takealook.api.domain.BaseTimeEntity;
import com.snp.takealook.api.domain.Selection;
import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.cat.CatCare;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.cat.CatCareDTO;
import com.snp.takealook.api.repository.SelectionRepository;
import com.snp.takealook.api.repository.cat.CatCareRepository;
import com.snp.takealook.api.repository.cat.CatRepository;
import com.snp.takealook.api.repository.user.UserRepository;
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
    private final UserRepository userRepository;
    private final CatRepository catRepository;
    private final SelectionRepository selectionRepository;

    @Transactional
    public Long save(Long userId, Long catId, CatCareDTO.Create dto) {
        Selection selection = selectionRepository.findSelectionByUser_IdAndCat_IdAndDflagFalse(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));

        return catCareRepository.save(dto.toEntity(selection)).getId();
    }

    @Transactional
    public Long update(Long userId, Long catId, Long catcareId, CatCareDTO.Update dto) {
        Selection selection = selectionRepository.findSelectionByUser_IdAndCat_IdAndDflagFalse(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));
        CatCare catCare = catCareRepository.findById(catcareId).orElseThrow(() -> new IllegalArgumentException("CatCare with id: " + catcareId + " is not valid"));

        if (!Objects.equals(catCare.getSelection().getId(), selection.getId())) {
            throw new IllegalStateException("only can update my catcare");
        }
        return catCare.update(dto.getType(), dto.getMessage()).getId();
    }

    @Transactional
    public void delete(Long userId, Long catId, Long catcareId) {
        Selection selection = selectionRepository.findSelectionByUser_IdAndCat_IdAndDflagFalse(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));
        CatCare catCare = catCareRepository.findById(catcareId).orElseThrow(() -> new IllegalArgumentException("CatCare with id: " + catcareId + " is not valid"));

        if (!Objects.equals(catCare.getSelection().getId(), selection.getId())) {
            throw new IllegalStateException("only can delete my catcare");
        }
        catCareRepository.delete(catCare);
    }

    @Transactional(readOnly = true)
    public List<ResponseDTO.CatCareListResponse> findByCatIdAndDate(Long catId, LocalDateTime dayStart, LocalDateTime dayEnd) {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));
        List<Selection> selectionList = selectionRepository.findSelectionsByCat(cat);

        List<CatCare> catCareList = new ArrayList<>();
        for (Selection selection : selectionList) {
            catCareList.addAll(catCareRepository.findCatCaresBySelectionAndCreatedAtBetween(selection, dayStart, dayEnd));
        }

        catCareList.sort(Comparator.comparing(BaseTimeEntity::getCreatedAt));

        return catCareList.stream()
                .map(ResponseDTO.CatCareListResponse::new)
                .collect(Collectors.toList());
    }

}
