package com.snp.takealook.api.service.cat;

import com.snp.takealook.api.domain.BaseTimeEntity;
import com.snp.takealook.api.domain.cat.*;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.cat.CatDTO;
import com.snp.takealook.api.repository.cat.SelectionRepository;
import com.snp.takealook.api.repository.cat.CatRepository;
import com.snp.takealook.api.repository.user.UserRepository;
import com.snp.takealook.api.service.S3Uploader;
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
    private final S3Uploader s3Uploader;

    @Transactional(rollbackFor = Exception.class)
    public Long save(CatDTO.Create dto, String image) {
        return catRepository.save(dto.toEntity(image)).getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public Long update(Long userId, Long catId, CatDTO.Update dto, String image) {
        Cat cat = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId)
                .orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid")).getCat();

        s3Uploader.fileDelete(cat.getImage());

        return cat.updateInfo(dto.getName(), dto.getGender(), dto.getNeutered(), dto.getPattern(), image).getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public Long changeStatus(Long userId, Long catId, Byte status) {
        Cat cat = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId)
                .orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid")).getCat();

        return cat.changeStatus(status).getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public Long changeDflag(Long userId, Long catId) {
        Cat cat = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId)
                .orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid")).getCat();

        return cat.sendCatStar().getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public Long changeAflag(Long userId, Long catId) {
        Cat cat = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId)
                .orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid")).getCat();

        return cat.adopt().getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long catId) {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Cat with id: " + catId + " is not valid"));

        if (cat.getSelectionList().size() == 0) {
            s3Uploader.fileDelete(cat.getImage());
            catRepository.delete(cat);
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
    public ResponseDTO.CatInfoResponse findCatInfoForUpdate(Long userId, Long catId) {
        Selection mySelection = selectionRepository.findSelectionByUser_IdAndCat_Id(userId, catId)
                .orElseThrow(() -> new IllegalArgumentException("Selection with userId: " + userId + " and catId: " + catId + " is not valid"));

        List<String> userUploadImages = new ArrayList<>();
        for (CatImage image : mySelection.getCatImageList()) {
            userUploadImages.add(image.getPath());
        }
        List<ResponseDTO.CatLocationResponse> userUploadLocations = mySelection.getCatLocationList().stream()
                .map(ResponseDTO.CatLocationResponse::new)
                .collect(Collectors.toList());

        return new ResponseDTO.CatInfoResponse(mySelection.getCat(), userUploadLocations, userUploadImages);
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
