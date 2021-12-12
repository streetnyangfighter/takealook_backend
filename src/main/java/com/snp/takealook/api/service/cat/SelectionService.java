package com.snp.takealook.api.service.cat;

import com.snp.takealook.api.domain.cat.Selection;
import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.repository.cat.SelectionRepository;
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
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));
        Cat pastCat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));
        Cat newCat = catRepository.findById(newCatId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + newCatId + " is not valid"));

        mySelection.update(newCat);

        // 다른 고양이를 추천받아 기존 고양이를 돌보는 사람이 0명일 경우, 해당 고양이 삭제
        // 를 해주려했는데 위에서 update를 했고, db 에도 수정된 데이터만 남았는데
        // 아래 size 는 0이 아닌 것으로 나와서 삭제가 되지 않음
        // 어거지로 삭제를 시키면 cat 아래 연관된 selection list 도 삭제됨ㅜ
        if (pastCat.getSelectionList().size() == 0) {
            catRepository.delete(pastCat);
        }

        return mySelection.getId();
    }

    // 간택 소프트 딜리트
    @Transactional
    public void softDelete(Long userId, Long catId) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId).orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));
        mySelection.delete();
    }

}
