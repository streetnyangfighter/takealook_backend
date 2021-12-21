package com.snp.takealook.api.controller.cat;

import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.cat.CatDTO;
import com.snp.takealook.api.service.cat.SecondaryService;
import com.snp.takealook.api.service.cat.SelectionService;
import com.snp.takealook.api.service.user.NotificationService;
import com.snp.takealook.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class SelectionController {

    private final SelectionService selectionService;
    private final SecondaryService secondaryService;
    private final NotificationService notificationService;

    @PostMapping("/user/{userId}/cat/{catId}/selection")
    public Long save(@AuthenticationPrincipal PrincipalDetails principal, @PathVariable Long catId) {
        User user = principal.getUser();
        selectionService.save(user.getId(), catId);
        notificationService.catSave(user.getId(), catId, (byte) 2);

        return catId;
    }

    //간택 정보 수정 - 기존 고양이 간택 + 자신이 작성한 정보 함께 이동
    @PatchMapping("/user/{userId}/cat/{catId}/selection")
    public Long update(@AuthenticationPrincipal PrincipalDetails principal, @PathVariable Long catId, Long newCatId) {
        User user = principal.getUser();

        return secondaryService.updateSelectionWithExistCat(user.getId(), catId, newCatId);
    }

    //간택 정보 수정 - 새로운 고양이 저장/간택 + 자신이 작성한 정보 함께 이동
    @PostMapping("/user/{userId}/cat/{catId}/selection/new")
    public Long updateNewCat(@AuthenticationPrincipal PrincipalDetails principal,
                             @PathVariable Long catId,
                             @RequestPart(value = "catInfo") CatDTO.Create catInfo,
                             @RequestPart(value = "catMainImg") MultipartFile file) throws IOException {
        User user = principal.getUser();

        return secondaryService.updateSelectionWithNewCat(user.getId(), catId, catInfo, file);
    }

    //간택 삭제(내가 작성한 정보 남김)
    @PatchMapping("/user/{userId}/cat/{catId}/selection/soft-delete")
    public void softDelete(@AuthenticationPrincipal PrincipalDetails principal, @PathVariable Long catId, boolean deleteCat) {
        User user = principal.getUser();

        selectionService.softDelete(user.getId(), catId);
        notificationService.catSave(user.getId(), catId, (byte) 4);
    }

}
