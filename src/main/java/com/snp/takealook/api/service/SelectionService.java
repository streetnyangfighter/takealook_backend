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
    public Long update(Long userId, Long catId, Long selectionId, Long newCatId) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));
        Selection selection = selectionRepository.findById(selectionId).orElseThrow(() -> new IllegalArgumentException("Selection with Id: " + selectionId + " is not valid"));

        if (!Objects.equals(mySelection, selection)) {
            throw new IllegalStateException("only can delete my selection");
        }

        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));

        return selection.update(cat).getId();
    }

    // 간택 소프트 딜리트
    @Transactional
    public void softDelete(Long userId, Long catId, Long selectionId) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));
        Selection selection = selectionRepository.findById(selectionId).orElseThrow(() -> new IllegalArgumentException("Selection with Id: " + selectionId + " is not valid"));

        if (!Objects.equals(mySelection, selection)) {
            throw new IllegalStateException("only can delete my selection");
        }

        selection.softDelete();
    }

    // 간택 하드 딜리트
    @Transactional
    public void hardDelete(Long userId, Long catId, Long selectionId) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));
        Selection selection = selectionRepository.findById(selectionId).orElseThrow(() -> new IllegalArgumentException("Selection with Id: " + selectionId + " is not valid"));

        if (!Objects.equals(mySelection, selection)) {
            throw new IllegalStateException("only can delete my selection");
        }

        //이거하면 고양이도 지워지는지 확인하고 수정해야함
        selectionRepository.delete(selection);
    }
}
