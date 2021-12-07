package com.snp.takealook.api.controller.cat;

import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.cat.CatCareDTO;
import com.snp.takealook.api.service.cat.CatCareService;
import com.snp.takealook.api.service.user.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class CatCareController {

    private final CatCareService catCareService;
    private final NotificationService notificationService;

    @PostMapping("/user/{userId}/cat/{catId}/catcare")
    public Long save(@PathVariable Long userId, @PathVariable Long catId, @RequestBody CatCareDTO.Create dto) {
        Long saveId = catCareService.save(catId, dto);
        notificationService.saveGroupNotification(userId, catId,(byte)1);
        return saveId;
    }

    @PutMapping("/user/{userId}/cat/{catId}/catcare/{catcareId}")
    public Long update(@PathVariable Long catcareId, @RequestBody CatCareDTO.Update dto) {
        return catCareService.update(catcareId, dto);
    }

    @DeleteMapping("/user/{userId}/cat/{catId}/catcare/{catcareId}")
    public Long delete(@PathVariable Long catcareId) {
        return catCareService.delete(catcareId);
    }

    @GetMapping("/user/{userId}/cat/{catId}/catcares")
    public List<ResponseDTO.CatCareListResponse> findAllByCatId(@PathVariable Long catId) {
        return catCareService.findAllByCatId(catId);
    }
}
