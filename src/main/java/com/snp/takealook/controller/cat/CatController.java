package com.snp.takealook.controller.cat;

import com.snp.takealook.dto.ResponseDTO;
import com.snp.takealook.dto.cat.CatDTO;
import com.snp.takealook.service.cat.CatImageService;
import com.snp.takealook.service.cat.CatLocationService;
import com.snp.takealook.service.cat.CatService;
import com.snp.takealook.service.cat.CatStatusService;
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

    @PostMapping(value = "/user/{userId}/cat", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public Long save(@PathVariable Long userId,
                     @RequestPart(value = "catInfo") CatDTO.Create catInfo,
                     @RequestPart(value = "catStatus") Byte status,
                     @RequestPart(value = "catLoc") List<CatDTO.LocationList> catLocList,
                     @RequestPart(value = "catImg") List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {
        Long catId = catService.save(userId, catInfo);
        catStatusService.save(catId, status);
        catLocationService.save(catId, catLocList);
        return catImageService.save(catId, files);
    }

    @PostMapping(value = "/user/{userId}/cat/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public Long update(@PathVariable Long id,
                       @RequestPart(value = "catInfo", required = false) CatDTO.Update catInfo,
                       @RequestPart(value = "catStatus", required = false) Byte status,
                       @RequestPart(value = "catLoc", required = false) List<CatDTO.LocationList> catLocList,
                       @RequestPart(value = "catImg", required = false) List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {
        catService.update(id, catInfo);
        catStatusService.save(id, status);
        catLocationService.update(id, catLocList);
        return catImageService.update(id, files);
    }

    @PostMapping("/user/{userId}/cat/{id}/status")
    public Long updateStatus(@PathVariable Long id, @RequestBody Byte status) {
        return catStatusService.save(id, status);
    }

    @PatchMapping("/user/{userId}/cat/{id}/delete")
    public Long delete(@PathVariable Long id) {
        return catService.delete(id);
    }

    @PatchMapping("/user/{userId}/cat/{id}/restore")
    public Long restore(@PathVariable Long id) {
        return catService.restore(id);
    }

    @PatchMapping("/user/{userId}/cat/{id}/groupout")
    public Long removeFromGroup(@PathVariable Long id) {
        return catService.removeFromGroup(id);
    }

    @GetMapping("/user/{userId}/cats")
    public List<ResponseDTO.CatListResponse> findAllByUserId(@PathVariable Long userId) {
        return catService.findAllByUserId(userId);
    }

    @GetMapping("/user/{userId}/cat/{id}")
    public ResponseDTO.CatResponse findOne(@PathVariable Long id) {
        return catService.findOne(id);
    }

    @GetMapping("/user/{userId}/cat/{id}/catlocations")
    public List<ResponseDTO.CatLocationListResponse> findAllLocationsById(@PathVariable Long id) {
        return catLocationService.findAllByCatId(id);
    }
}
