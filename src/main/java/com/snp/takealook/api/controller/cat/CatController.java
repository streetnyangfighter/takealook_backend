package com.snp.takealook.api.controller.cat;

import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.cat.CatDTO;
import com.snp.takealook.api.service.cat.*;
import com.snp.takealook.api.service.user.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class CatController {

    private final CatService catService;
    private final SecondaryService secondaryService;
    private final CatLocationService catLocationService;
    private final CatImageService catImageService;
    private final NotificationService notificationService;

    @PostMapping(value = "/user/{userId}/cat/selection", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long save(@PathVariable Long userId,
                           @RequestPart(value = "catInfo") CatDTO.Create catInfo,
                           @RequestPart(value = "catLoc") CatDTO.LocationList[] catLocList,
                           @RequestPart(value = "catMainImg") MultipartFile file,
                           @RequestPart(value = "catImg", required = false) List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {

        return secondaryService.saveNewCat(userId, catInfo, catLocList, file, files);
    }

    @PostMapping(value = "/user/{userId}/cat/{catId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long update(@PathVariable Long userId,
                       @PathVariable Long catId,
                       @RequestPart(value = "catInfo") CatDTO.Update catInfo,
                       @RequestPart(value = "catLoc") CatDTO.LocationList[] catLocList,
                       @RequestPart(value = "catMainImg") MultipartFile file,
                       @RequestPart(value = "catImg", required = false) List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {

        return secondaryService.updateCat(userId, catId, catInfo, catLocList, file, files);
    }

    @PatchMapping("/user/{userId}/cat/{catId}")
    public Long changeStatus(@PathVariable Long userId, @PathVariable Long catId, Byte status) {
        Long updateId = catService.changeStatus(userId, catId, status);
        notificationService.catSave(userId, catId, (byte) 1);
        return updateId;
    }

    @PatchMapping("/user/{userId}/cat/{catId}/cat-star")
    public Long changeDflag(@PathVariable Long userId, @PathVariable Long catId, @RequestBody String msg) {
        Long updateId = catService.changeDflag(userId, catId);
        notificationService.catSave(userId, catId, (byte) 6);
        return updateId;
    }

    @PatchMapping("/user/{userId}/cat/{catId}/adoptation")
    public Long changeAflag(@PathVariable Long userId, @PathVariable Long catId, @RequestBody String msg) {
        Long updateId = catService.changeAflag(userId, catId);
        notificationService.catSave(userId, catId, (byte) 7);
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

    @GetMapping("/user/{userId}/cat-stars")
    public List<ResponseDTO.CatStarResponse> findAllCatStarsByUserId(@PathVariable Long userId) {
        return catService.findAllCatStarsByUserId(userId);
    }

    @GetMapping("/user/{userId}/adopted")
    public List<ResponseDTO.CatAdoptedResponse> findAllAdoptedByUserId(@PathVariable Long userId) {
        return catService.findAllAdoptedByUserId(userId);
    }

    @GetMapping("/user/{userId}/cats/recent-location")
    public List<ResponseDTO.CatListResponse> findMyCatsRecentLocation(@PathVariable Long userId) {
        return catService.findMyCatsRecentLocation(userId);
    }

    @GetMapping("/user/{userId}/cat/{catId}/locations")
    public List<ResponseDTO.CatLocationResponse> findLocationsByCatId(@PathVariable Long userId, @PathVariable Long catId) {
        return catLocationService.findLocationsByCatId(userId, catId);
    }

    @GetMapping("/user/{userId}/cat/{catId}/images")
    public List<ResponseDTO.CatImageListResponse> findImagesByCatId(@PathVariable Long userId, @PathVariable Long catId) {
        return catImageService.findImagesByCatId(userId, catId);
    }
}
