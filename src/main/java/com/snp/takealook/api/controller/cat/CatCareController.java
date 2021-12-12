package com.snp.takealook.api.controller.cat;

import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.cat.CatCareDTO;
import com.snp.takealook.api.service.cat.CatCareService;
import com.snp.takealook.api.service.user.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class CatCareController {

    private final CatCareService catCareService;
    private final NotificationService notificationService;

    @PostMapping("/user/{userId}/cat/{catId}/catcare")
    public Long save(@PathVariable Long userId, @PathVariable Long catId, @RequestBody CatCareDTO.Create dto) {
        Long saveId = catCareService.save(userId, catId, dto);
        notificationService.save(userId, catId, (byte) 0);
        return saveId;
    }

    @PatchMapping("/user/{userId}/cat/{catId}/catcare/{catcareId}")
    public Long update(@PathVariable Long userId, @PathVariable Long catId, @PathVariable Long catcareId, @RequestBody CatCareDTO.Update dto) {
        return catCareService.update(userId, catId, catcareId, dto);
    }

    @DeleteMapping("/user/{userId}/cat/{catId}/catcare/{catcareId}")
    public void delete(@PathVariable Long userId, @PathVariable Long catId, @PathVariable Long catcareId) {
        catCareService.delete(userId, catId, catcareId);
    }

    @GetMapping("/user/{userId}/cat/{catId}/monthly-catcares")
    public List<ResponseDTO.CatCareListResponse> findMonthlyByCatId(@PathVariable Long catId, int year, int month) {
        LocalDateTime dayStart = LocalDateTime.of(year, month, 1, 00, 00);
        LocalDateTime dayEnd = dayStart.plusMonths(1);

        return catCareService.findByCatIdAndDate(catId, dayStart, dayEnd);
    }

    @GetMapping("/user/{userId}/cat/{catId}/48hours-catcares")
    public List<ResponseDTO.CatCareListResponse> findRecentsByCatId(@PathVariable Long catId) {
        LocalDateTime dayStart = LocalDateTime.now().minusDays(2);
        LocalDateTime dayEnd = LocalDateTime.now();

        return catCareService.findByCatIdAndDate(catId, dayStart, dayEnd);
    }
}
