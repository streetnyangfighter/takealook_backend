package com.snp.takealook.api.controller.cat;

import com.amazonaws.services.ec2.model.UpdateSecurityGroupRuleDescriptionsIngressRequest;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.cat.CatDTO;
import com.snp.takealook.api.service.cat.*;
import com.snp.takealook.api.service.user.NotificationService;
import com.snp.takealook.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public Long save(@AuthenticationPrincipal PrincipalDetails principal,
                     @RequestPart(value = "catInfo") CatDTO.Create catInfo,
                     @RequestPart(value = "catLoc") CatDTO.LocationList[] catLocList,
                     @RequestPart(value = "catMainImg") MultipartFile file,
                     @RequestPart(value = "catImg", required = false) List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {
        User user = principal.getUser();

        return secondaryService.saveNewCat(user.getId(), catInfo, catLocList, file, java.util.Optional.ofNullable(files));
    }

    @PostMapping(value = "/user/{userId}/cat/{catId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long update(@AuthenticationPrincipal PrincipalDetails principal,
                       @PathVariable Long catId,
                       @RequestPart(value = "catInfo") CatDTO.Update catInfo,
                       @RequestPart(value = "catLoc") CatDTO.LocationList[] catLocList,
                       @RequestPart(value = "catMainImg", required = false) MultipartFile file,
                       @RequestPart(value = "deletedImgUrl", required = false) String[] deletedImgUrl,
                       @RequestPart(value = "catImg", required = false) List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {
        User user = principal.getUser();

        return secondaryService.updateCat(user.getId(), catId, catInfo, catLocList, java.util.Optional.ofNullable(file), java.util.Optional.ofNullable(deletedImgUrl), java.util.Optional.ofNullable(files));
    }

    @PatchMapping("/user/{userId}/cat/{catId}")
    public Long changeStatus(@AuthenticationPrincipal PrincipalDetails principal, @PathVariable Long catId, Byte status) {
        User user = principal.getUser();
        Long updateId = catService.changeStatus(user.getId(), catId, status);
        notificationService.catSave(user.getId(), catId, (byte) 1);

        return updateId;
    }

    @PatchMapping("/user/{userId}/cat/{catId}/cat-star")
    public Long changeDflag(@AuthenticationPrincipal PrincipalDetails principal, @PathVariable Long catId, @RequestBody String msg) {
        User user = principal.getUser();
        Long updateId = catService.changeDflag(user.getId(), catId);
        notificationService.catSave(user.getId(), catId, (byte) 6);

        return updateId;
    }

    @PatchMapping("/user/{userId}/cat/{catId}/adoptation")
    public Long changeAflag(@AuthenticationPrincipal PrincipalDetails principal, @PathVariable Long catId, @RequestBody String msg) {
        User user = principal.getUser();
        Long updateId = catService.changeAflag(user.getId(), catId);
        notificationService.catSave(user.getId(), catId, (byte) 7);

        return updateId;
    }

    @GetMapping("/user/{userId}/cat/{catId}")
    public ResponseDTO.CatResponse findOne(@AuthenticationPrincipal PrincipalDetails principal, @PathVariable Long catId) {
        User user = principal.getUser();

        return catService.findOne(user.getId(), catId);
    }

    @GetMapping("/user/{userId}/cat/{catId}/past-info")
    public ResponseDTO.CatInfoResponse findCatInfoForUpdate(@AuthenticationPrincipal PrincipalDetails principal, @PathVariable Long catId) {
        User user = principal.getUser();

        return catService.findCatInfoForUpdate(user.getId(), catId);
    }

    @GetMapping("/user/{userId}/cats")
    public List<ResponseDTO.CatListResponse> findAllByUserId(@AuthenticationPrincipal PrincipalDetails principal) {
        User user = principal.getUser();

        return catService.findAllByUserId(user.getId());
    }

    @GetMapping("/user/{userId}/cat-stars")
    public List<ResponseDTO.CatStarResponse> findAllCatStarsByUserId(@AuthenticationPrincipal PrincipalDetails principal) {
        User user = principal.getUser();

        return catService.findAllCatStarsByUserId(user.getId());
    }

    @GetMapping("/user/{userId}/adopted")
    public List<ResponseDTO.CatAdoptedResponse> findAllAdoptedByUserId(@AuthenticationPrincipal PrincipalDetails principal) {
        User user = principal.getUser();

        return catService.findAllAdoptedByUserId(user.getId());
    }

    @GetMapping("/user/{userId}/cats/recent-location")
    public List<ResponseDTO.CatListResponse> findMyCatsRecentLocation(@AuthenticationPrincipal PrincipalDetails principal) {
        User user = principal.getUser();

        return catService.findMyCatsRecentLocation(user.getId());
    }

    @GetMapping("/user/{userId}/cat/{catId}/locations")
    public List<ResponseDTO.CatLocationResponse> findLocationsByCatId(@AuthenticationPrincipal PrincipalDetails principal, @PathVariable Long catId) {
        User user = principal.getUser();

        return catLocationService.findLocationsByCatId(user.getId(), catId);
    }

    @GetMapping("/user/{userId}/cat/{catId}/images")
    public List<ResponseDTO.CatImageListResponse> findImagesByCatId(@AuthenticationPrincipal PrincipalDetails principal, @PathVariable Long catId) {
        User user = principal.getUser();

        return catImageService.findImagesByCatId(user.getId(), catId);
    }

    @GetMapping("/test")
    public List<ResponseDTO.CatLocationResponse> findLocationInMap() {
        return catLocationService.getNearByCats(37.4901548250937, 127.030767490957,1.0);
    }
}
