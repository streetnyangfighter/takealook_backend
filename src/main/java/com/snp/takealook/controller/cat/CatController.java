package com.snp.takealook.controller.cat;

import com.snp.takealook.dto.ResponseDTO;
import com.snp.takealook.dto.cat.CatDTO;
import com.snp.takealook.service.cat.CatImageService;
import com.snp.takealook.service.cat.CatService;
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
    private final CatImageService catImageService;

    @PostMapping(value = "/cat", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public Long save(@RequestPart(value = "catInfo") CatDTO.Create dto, @RequestPart(value = "catImg", required = false) List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {
        Long catId = catService.save(dto);
        return catImageService.save(catId, files);
    }

    @PostMapping(value = "/cat/info/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public Long updateInfo(@PathVariable Long id, @RequestPart(value = "catInfo", required = false) CatDTO.Update dto, @RequestPart(value = "catLoc", required = false) List<CatDTO.LocationList> dtoList, @RequestPart(value = "catImg", required = false) List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {
        catService.updateInfo(id, dto);
        catService.updateLocations(id, dtoList);
        return catImageService.update(id, files);
    }

    @PutMapping("/cat/locations/{id}")
    public Long updateLocations(@PathVariable Long id, @RequestBody List<CatDTO.LocationList> dtoList) {
        return catService.updateLocations(id, dtoList);
    }

    @PatchMapping("/cat/status/{id}")
    public Long updateStatus(@PathVariable Long id, @RequestBody CatDTO.Update dto) {
        return catService.updateStatus(id, dto);
    }

    @PatchMapping("/cat/delete/{id}")
    public Long delete(@PathVariable Long id) {
        return catService.delete(id);
    }

    @PatchMapping("/cat/restore/{id}")
    public Long restore(@PathVariable Long id) {
        return catService.restore(id);
    }

    @PatchMapping("/cat/groupout/{id}")
    public Long removeFromGroup(@PathVariable Long id) {
        return catService.removeFromGroup(id);
    }

    @GetMapping("/cat/user/{userId}")
    public List<ResponseDTO.CatListResponse> findAllByUserId(@PathVariable Long userId) {
        return catService.findAllByUserId(userId);
    }

    @GetMapping("/cat/{id}")
    public ResponseDTO.CatResponse findOne(@PathVariable Long id) {
        return catService.findOne(id);
    }
}
