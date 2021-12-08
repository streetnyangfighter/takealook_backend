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
        Long saveId = catCareService.save(catId, dto);
        notificationService.saveGroupNotification(userId, catId, (byte) 1);
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

    @GetMapping("/user/{userId}/cat/{catId}/catcares/monthly")
    public List<ResponseDTO.CatCareListResponse> findMonthlyByCatId(@PathVariable Long catId, int year, int month) {
        LocalDateTime dayStart = LocalDateTime.of(year, month, 1, 00, 00);
        LocalDateTime dayEnd = dayStart.plusMonths(1);
//        System.out.println(dayStart + " *** " + dayEnd);

        return catCareService.findByCatIdAndDate(catId, dayStart, dayEnd);
    }

    @GetMapping("/user/{userId}/cat/{catId}/catcares/48hours")
    public List<ResponseDTO.CatCareListResponse> findRecentsByCatId(@PathVariable Long catId) {
        LocalDateTime dayStart = LocalDateTime.now().minusDays(2);
        LocalDateTime dayEnd = LocalDateTime.now();
//        System.out.println(dayStart + " *** " + dayEnd);

        return catCareService.findByCatIdAndDate(catId, dayStart, dayEnd);
    }
}
