package com.snp.takealook.api.service.cat;

import com.snp.takealook.api.domain.Selection;
import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.cat.CatDTO;
import com.snp.takealook.api.repository.SelectionRepository;
import com.snp.takealook.api.repository.cat.CatRepository;
import com.snp.takealook.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatService {

    private final CatRepository catRepository;
    private final UserRepository userRepository;
    private final SelectionRepository selectionRepository;

    @Transactional
    public Selection validationCheck(Long userId, Long catId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));

        return selectionRepository.findSelectionByUserAndCat(user, cat).orElseThrow(() -> new IllegalArgumentException("Match with userId: " + userId + " and catId: " + catId + " is not valid"));
    }

    @Transactional
    public Long save(CatDTO.Create dto) {
        return catRepository.save(dto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long userId, Long catId, CatDTO.Update dto) {
        Cat cat = validationCheck(userId, catId).getCat();

        return cat.updateInfo(dto.getName(), dto.getGender(), dto.getNeutered(), dto.getStatus(), dto.getPattern()).getId();
    }

    @Transactional
    public Long changeStatus(Long userId, Long catId, Byte status) {
        Cat cat = validationCheck(userId, catId).getCat();

        return cat.changeStatus(status).getId();
    }

    // 고양이 상세 조회 보류(함께 돌보는 사람들, 이미지 함께?)
    @Transactional(readOnly = true)
    public ResponseDTO.CatResponse findOne(Long userId, Long catId) {
        Selection selection = validationCheck(userId, catId);

        return new ResponseDTO.CatResponse(selection.getCat());
    }

    @Transactional(readOnly = true)
    public List<ResponseDTO.CatListResponse> findAllByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));
        List<Selection> selectionList = selectionRepository.findSelectionsByUser(user);

        List<Cat> userCatList = new ArrayList<>();
        for (Selection selection : selectionList) {
            userCatList.add(selection.getCat());
        }

        return userCatList.stream()
                .map(ResponseDTO.CatListResponse::new)
                .collect(Collectors.toList());
    }
}
