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

@RequiredArgsConstructor
@Service
public class SelectionService {

    private final SelectionRepository selectionRepository;
    private final UserRepository userRepository;
    private final CatRepository catRepository;

    // 매치 추가하기
    @Transactional
    public Long save(Long userId, Long catId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));

        return selectionRepository.save(Selection.builder()
                .user(user)
                .cat(cat)
                .build()).getId();
    }

    // 매치 삭제하기 -> 하면 이전 정보 어떻게 할 지 보류
}
