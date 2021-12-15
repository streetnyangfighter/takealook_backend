package com.snp.takealook.api.service.cat;

import com.snp.takealook.api.domain.cat.Selection;
import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.repository.cat.MainImageRepository;
import com.snp.takealook.api.repository.cat.SelectionRepository;
import com.snp.takealook.api.repository.cat.CatRepository;
import com.snp.takealook.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SelectionService {

    private final SelectionRepository selectionRepository;
    private final UserRepository userRepository;
    private final CatRepository catRepository;
    private final MainImageRepository mainImageRepository;

    // 간택 추가하기
    @Transactional
    public Long save(Long userId, Long catId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));

        Optional<Selection> exist = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId);
        if (exist.isPresent()) {
            throw new IllegalStateException("이전에 도감에서 삭제한 고양이입니다.");
        }

        return selectionRepository.save(Selection.builder()
                .user(user)
                .cat(cat)
                .build()).getId();
    }

    // 간택 정보 업데이트
    @Transactional
    public Long update(Long userId, Long catId, Long newCatId) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));
        Cat pastCat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));
        Cat newCat = catRepository.findById(newCatId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + newCatId + " is not valid"));

        mySelection.update(newCat);

        return mySelection.getId();
    }

    // 간택 소프트 딜리트
    @Transactional
    public void softDelete(Long userId, Long catId) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));
        mySelection.delete();
    }

}
