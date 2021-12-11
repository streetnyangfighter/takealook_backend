package com.snp.takealook.api.service;

import com.snp.takealook.api.domain.Selection;
import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.repository.SelectionRepository;
import com.snp.takealook.api.repository.cat.CatRepository;
import com.snp.takealook.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class SelectionService {

    private final SelectionRepository selectionRepository;
    private final UserRepository userRepository;
    private final CatRepository catRepository;

    // 간택 추가하기
    @Transactional
    public Long save(Long userId, Long catId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));

        return selectionRepository.save(Selection.builder()
                .user(user)
                .cat(cat)
                .build()).getId();
    }

    // 간택 정보 업데이트
    @Transactional
    public Long update(Long userId, Long catId, Long newCatId) {
        // 자기가 이미 돌보고 있는 고양이에 다시 연결하는 일은 없도록 조치해야함
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));
        Cat cat = catRepository.findById(newCatId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));

        return mySelection.update(cat).getId();
    }

    // 간택 소프트 딜리트
    @Transactional
    public void softDelete(Long userId, Long catId, boolean deleteCat) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));

        mySelection.setUserNull();

        if (deleteCat) { // 해당 고양이를 돌보는 유일한 유저가 도감에서 고양이를 삭제했을 경우 고양이도 함께 삭제
            catRepository.delete(mySelection.getCat());
        }
    }

    // 간택 하드 딜리트
    @Transactional
    public void hardDelete(Long userId, Long catId) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));

        selectionRepository.delete(mySelection);
    }

}
