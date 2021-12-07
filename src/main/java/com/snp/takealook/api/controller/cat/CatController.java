package com.snp.takealook.api.controller.cat;

import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.cat.CatDTO;
import com.snp.takealook.api.dto.cat.CatStatusDTO;
import com.snp.takealook.api.service.cat.CatImageService;
import com.snp.takealook.api.service.cat.CatLocationService;
import com.snp.takealook.api.service.cat.CatService;
import com.snp.takealook.api.service.cat.CatStatusService;
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
    private final CatStatusService catStatusService;
    private final CatLocationService catLocationService;
    private final CatImageService catImageService;
    private final NotificationService notificationService;

    @PostMapping(value = "/user/{userId}/cat", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long save(@PathVariable Long userId,
                     @RequestPart(value = "catInfo") CatDTO.Create catInfo,
                     @RequestPart(value = "catStatus") CatStatusDTO.Create catStatus,
                     @RequestPart(value = "catLoc") List<CatDTO.LocationList> catLocList,
                     @RequestPart(value = "catImg") List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {
        Long catId = catService.save(userId, catInfo);
        catStatusService.save(catId, catStatus);
        notificationService.saveGroupNotification(userId, catId, (byte)2);
        catLocationService.save(catId, catLocList);
        return catImageService.save(catId, files);
    }

    @PostMapping(value = "/user/{userId}/cat/{catId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long update(@PathVariable Long userId,
                       @PathVariable Long catId,
                       @RequestPart(value = "catInfo", required = false) CatDTO.Update catInfo,
                       @RequestPart(value = "catStatus", required = false) CatStatusDTO.Create catStatus,
                       @RequestPart(value = "catLoc", required = false) List<CatDTO.LocationList> catLocList,
                       @RequestPart(value = "catImg", required = false) List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {
        catService.update(catId, catInfo);
        catStatusService.save(catId, catStatus);
        notificationService.saveGroupNotification(userId, catId, (byte)2);
        catLocationService.update(catId, catLocList);
        return catImageService.update(catId, files);
    }

    @PostMapping("/user/{userId}/cat/{catId}/status")
    public Long saveStatus(@PathVariable Long catId, @RequestBody CatStatusDTO.Create catStatus) {
        return catStatusService.save(catId, catStatus);
    }

    @PatchMapping("/user/{userId}/cat/{catId}/delete")
    public Long delete(@PathVariable Long catId) {
        return catService.delete(catId);
    }

    @PatchMapping("/user/{userId}/cat/{catId}/restore")
    public Long restore(@PathVariable Long catId) {
        return catService.restore(catId);
    }

    @PatchMapping("/user/{userId}/cat/{catId}/groupout")
    public Long removeFromGroup(@PathVariable Long userId, @PathVariable Long catId) {
        catService.removeFromGroup(catId);
        notificationService.saveGroupNotification(userId, catId, (byte)6);
        return catId;
    }

    @GetMapping("/user/{userId}/cats")
    public List<ResponseDTO.CatListResponse> findAllByUserId(@PathVariable Long userId) {
        return catService.findAllByUserId(userId);
    }

    @GetMapping("/user/{userId}/cat/{catId}")
    public ResponseDTO.CatResponse findOne(@PathVariable Long catId) {
        return catService.findOne(catId);
    }

    @GetMapping("/user/{userId}/cat/{catId}/catlocations")
    public List<ResponseDTO.CatLocationListResponse> findAllLocationsById(@PathVariable Long catId) {
        return catLocationService.findAllByCatId(catId);
    }

    @GetMapping("/user/{userId}/cat/{id}/catimages")
    public List<ResponseDTO.CatImageListResponse> findAllImagesById(@PathVariable Long id) {
        return catImageService.findAllByCatId(id);
    }
}
