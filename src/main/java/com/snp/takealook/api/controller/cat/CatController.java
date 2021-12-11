package com.snp.takealook.api.controller.cat;

import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.cat.CatDTO;
import com.snp.takealook.api.service.SelectionService;
import com.snp.takealook.api.service.cat.CatImageService;
import com.snp.takealook.api.service.cat.CatLocationService;
import com.snp.takealook.api.service.cat.CatService;
import com.snp.takealook.api.service.user.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class CatController {

    private final CatService catService;
    private final SelectionService selectionService;
    private final CatLocationService catLocationService;
    private final CatImageService catImageService;
    private final NotificationService notificationService;

    @PostMapping(value = "/user/{userId}/cat/selection", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long save(@PathVariable Long userId,
                           @RequestPart(value = "catInfo") CatDTO.Create catInfo,
                           @RequestPart(value = "catLoc") CatDTO.LocationList[] catLocList,
                           @RequestPart(value = "catImg", required = false) List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {
        Long catId = catService.save(catInfo);
        Long selectionId = selectionService.save(userId, catId);
        catLocationService.saveAll(selectionId, catLocList);
        catImageService.save(selectionId, files);

        return catId;
    }

    @PostMapping(value = "/user/{userId}/cat/{catId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long update(@PathVariable Long userId,
                       @PathVariable Long catId,
                       @RequestPart(value = "catInfo", required = false) CatDTO.Update catInfo,
                       @RequestPart(value = "catLoc", required = false) CatDTO.LocationList[] catLocList,
                       @RequestPart(value = "catImg", required = false) List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {
        catService.update(userId, catId, catInfo);
        catLocationService.update(userId, catId, catLocList);
        catImageService.update(userId, catId, files);

        return catId;
    }

    @PatchMapping("/user/{userId}/cat/{catId}")
    public Long changeStatus(@PathVariable Long userId, @PathVariable Long catId, Byte status) {
        Long updateId = catService.changeStatus(userId, catId, status);
        notificationService.save(userId, catId, (byte) 1);
        return updateId;
    }

    @GetMapping("/user/{userId}/cat/{catId}")
    public ResponseDTO.CatResponse findOne(@PathVariable Long userId, @PathVariable Long catId) {
        return catService.findOne(userId, catId);
    }

    @GetMapping("/user/{userId}/cats")
    public List<ResponseDTO.CatListResponse> findAllByUserId(@PathVariable Long userId) {
        return catService.findAllByUserId(userId);
    }

    @GetMapping("/user/{userId}/cats/recent-location")
    public List<ResponseDTO.CatListResponse> findMyCatsRecentLocation(@PathVariable Long userId) {
        return catService.findMyCatsRecentLocation(userId);
    }

    @GetMapping("/user/{userId}/cat/{catId}/locations")
    public List<ResponseDTO.CatLocationResponse> findLocationsByCatId(@PathVariable Long userId, @PathVariable Long catId) {
        return catLocationService.findLocationsByCatId(userId, catId);
    }

    // 고양이별 이미지 전체 조회 -> 프론트에 보내줘야 할 값 정확히 확인
    @GetMapping("/user/{userId}/cat/{catId}/images")
    public List<File> findImagesByCatId(@PathVariable Long userId, @PathVariable Long catId) {
        return catImageService.findImagesByCatId(userId, catId);
    }
}
