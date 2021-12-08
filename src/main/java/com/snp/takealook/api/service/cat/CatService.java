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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatService {

    private final CatRepository catRepository;
    private final UserRepository userRepository;
    private final SelectionRepository selectionRepository;

    @Transactional
    public Long save(CatDTO.Create dto) {
        return catRepository.save(dto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long userId, Long catId, CatDTO.Update dto) {
        Cat cat = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId)
                .orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid")).getCat();

        return cat.updateInfo(dto.getName(), dto.getGender(), dto.getNeutered(), dto.getStatus(), dto.getPattern()).getId();
    }

    @Transactional
    public Long changeStatus(Long userId, Long catId, Byte status) {
        Cat cat = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId)
                .orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid")).getCat();

        return cat.changeStatus(status).getId();
    }

    // 고양이 상세 조회 보류(함께 돌보는 사람들, 이미지 함께?)
    @Transactional(readOnly = true)
    public ResponseDTO.CatResponse findOne(Long userId, Long catId) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId)
                .orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));

        List<Selection> selectionList = selectionRepository.findSelectionsByCat(mySelection.getCat());
        Map<String, String> carers = new HashMap<>();
        for (Selection selection : selectionList) {
            User user = selection.getUser();
            carers.put(user.getId().toString() + " / " + user.getNickname(), user.getImage());
        }

        return new ResponseDTO.CatResponse(mySelection.getCat(), carers);
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
