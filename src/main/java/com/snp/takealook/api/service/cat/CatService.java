package com.snp.takealook.api.service.cat;

import com.snp.takealook.api.domain.BaseTimeEntity;
import com.snp.takealook.api.domain.cat.Selection;
import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.cat.CatCare;
import com.snp.takealook.api.domain.cat.CatLocation;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.cat.CatDTO;
import com.snp.takealook.api.repository.cat.MainImageRepository;
import com.snp.takealook.api.repository.cat.SelectionRepository;
import com.snp.takealook.api.repository.cat.CatRepository;
import com.snp.takealook.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatService {

    private final CatRepository catRepository;
    private final UserRepository userRepository;
    private final SelectionRepository selectionRepository;
    private final MainImageRepository mainImageRepository;

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

    @Transactional
    public Long changeDflag(Long userId, Long catId) {
        Cat cat = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId)
                .orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid")).getCat();

        return cat.sendCatStar().getId();
    }

    @Transactional
    public Long changeAflag(Long userId, Long catId) {
        Cat cat = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId)
                .orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid")).getCat();

        return cat.adopt().getId();
    }

    @Transactional
    public void delete(Long catId) {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));

        if (cat.getSelectionList().size() == 0) {

//            mainImageRepository.delete(cat.getMainImage());
            File exist = new File(cat.getMainImage().getFilePath());
            if (exist.exists()) {
                exist.delete();
            } else {
                System.out.println("file not exists");
            }
            // selection 에 연결된 이미지도 다 지워줘야함

            catRepository.delete(cat); //이거만 하면 됨
        }
    }

    @Transactional(readOnly = true)
    public ResponseDTO.CatResponse findOne(Long userId, Long catId) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId)
                .orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));

        List<Selection> selectionList = selectionRepository.findSelectionsByCatAndDflagFalse(mySelection.getCat());
        List<ResponseDTO.UserInfo> carers = new ArrayList<>();
        for (Selection selection : selectionList) {
            if (!selection.getUser().getDflag()) {
                carers.add(new ResponseDTO.UserInfo(selection.getUser()));
            }
        }

        return new ResponseDTO.CatResponse(mySelection.getCat(), carers);
    }

    @Transactional(readOnly = true)
    public List<ResponseDTO.CatListResponse> findAllByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));
        List<Selection> selectionList = selectionRepository.findSelectionsByUserAndDflagFalse(user);
        List<ResponseDTO.CatListResponse> result = new ArrayList<>();

        List<Cat> userCatList = new ArrayList<>();
        for (Selection selection : selectionList) {
            if (!selection.getCat().getDflag() && !selection.getCat().getAflag()) {
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

        }

        return result;
    }

    @Transactional(readOnly = true)
    public List<ResponseDTO.CatStarResponse> findAllCatStarsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));
        List<Selection> selectionList = selectionRepository.findSelectionsByUserAndDflagFalse(user);
        List<ResponseDTO.CatStarResponse> result = new ArrayList<>();

        for (Selection selection : selectionList) {
            if (selection.getCat().getDflag()) {
                result.add(new ResponseDTO.CatStarResponse(selection.getCat()));
            }
        }

        return result;
    }

    @Transactional(readOnly = true)
    public List<ResponseDTO.CatAdoptedResponse> findAllAdoptedByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));
        List<Selection> selectionList = selectionRepository.findSelectionsByUserAndDflagFalse(user);
        List<ResponseDTO.CatAdoptedResponse> result = new ArrayList<>();

        for (Selection selection : selectionList) {
            if (selection.getCat().getAflag()) {
                result.add(new ResponseDTO.CatAdoptedResponse(selection.getCat()));
            }
        }

        return result;
    }

    @Transactional(readOnly = true)
    public List<ResponseDTO.CatListResponse> findMyCatsRecentLocation(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));
        List<Selection> selectionList = selectionRepository.findSelectionsByUserAndDflagFalse(user);
        List<ResponseDTO.CatListResponse> result = new ArrayList<>();

        List<Cat> userCatList = new ArrayList<>();
        for (Selection selection : selectionList) {
            if (!selection.getCat().getDflag() && !selection.getCat().getAflag()) {
                userCatList.add(selection.getCat());

                List<Selection> sameCatSelectionList = selection.getCat().getSelectionList();
                List<CatLocation> recentLocations = new ArrayList<>();
                for (Selection sameCatSelection : sameCatSelectionList) {
                    recentLocations.addAll(sameCatSelection.getCatLocationList());
                }

                recentLocations.sort(Comparator.comparing(BaseTimeEntity::getCreatedAt).reversed());

                result.add(new ResponseDTO.CatListResponse(selection.getCat(), new ResponseDTO.CatLocationResponse(recentLocations.get(0))));
            }

        }

        return result;
    }

}
