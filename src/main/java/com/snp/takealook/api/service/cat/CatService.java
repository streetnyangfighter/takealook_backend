package com.snp.takealook.api.service.cat;

import com.snp.takealook.api.domain.BaseTimeEntity;
import com.snp.takealook.api.domain.Selection;
import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.cat.CatCare;
import com.snp.takealook.api.domain.cat.CatLocation;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.cat.CatDTO;
import com.snp.takealook.api.repository.SelectionRepository;
import com.snp.takealook.api.repository.cat.CatRepository;
import com.snp.takealook.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

    @Transactional(readOnly = true)
    public ResponseDTO.CatResponse findOne(Long userId, Long catId) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId)
                .orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));

        List<Selection> selectionList = mySelection.getCat().getSelectionList();
        List<ResponseDTO.UserInfo> carers = new ArrayList<>();
        for (Selection selection : selectionList) {
            // 유저가 null이 아닌지도 체크해줘야함
            if (!selection.getUser().getDflag()) {
                    carers.add(new ResponseDTO.UserInfo(selection.getUser()));
            }
        }

        return new ResponseDTO.CatResponse(mySelection.getCat(), carers);
    }

    @Transactional(readOnly = true)
    public List<ResponseDTO.CatListResponse> findAllByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));
        List<Selection> selectionList = user.getSelectionList();
        List<ResponseDTO.CatListResponse> result = new ArrayList<>();

        List<Cat> userCatList = new ArrayList<>();
        for (Selection selection : selectionList) {
            userCatList.add(selection.getCat());

            List<Selection> sameCatSelectionList = selection.getCat().getSelectionList();
            List<CatCare> recentCares = new ArrayList<>();
            for (Selection sameCatSelection : sameCatSelectionList) {
                recentCares.addAll(sameCatSelection.getCatCareList());
            }

            recentCares.sort(Comparator.comparing(BaseTimeEntity::getCreatedAt).reversed());
            if (recentCares.size() > 3) {
                recentCares = recentCares.subList(0, 3);
            }

            List<ResponseDTO.CatCareListResponse> recentCaresResponse = recentCares.stream()
                    .map(ResponseDTO.CatCareListResponse::new)
                    .collect(Collectors.toList());

            result.add(new ResponseDTO.CatListResponse(selection.getCat(), recentCaresResponse));
        }

        return result;
    }

    @Transactional(readOnly = true)
    public List<ResponseDTO.CatListResponse> findMyCatsRecentLocation(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));
        List<Selection> selectionList = user.getSelectionList();
        List<ResponseDTO.CatListResponse> result = new ArrayList<>();

        List<Cat> userCatList = new ArrayList<>();
        for (Selection selection : selectionList) {
            userCatList.add(selection.getCat());

            List<Selection> sameCatSelectionList = selection.getCat().getSelectionList();
            List<CatLocation> recentLocations = new ArrayList<>();
            for (Selection sameCatSelection : sameCatSelectionList) {
                recentLocations.addAll(sameCatSelection.getCatLocationList());
            }

            recentLocations.sort(Comparator.comparing(BaseTimeEntity::getCreatedAt).reversed());

            result.add(new ResponseDTO.CatListResponse(selection.getCat(), new ResponseDTO.CatLocationResponse(recentLocations.get(0))));
        }

        return result;
    }

}
