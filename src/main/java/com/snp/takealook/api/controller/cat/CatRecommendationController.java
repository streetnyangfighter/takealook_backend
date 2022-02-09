package com.snp.takealook.api.controller.cat;

import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.cat.CatLocation;
import com.snp.takealook.api.domain.cat.Selection;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.cat.CatDTO;
import com.snp.takealook.api.service.S3Uploader;
import com.snp.takealook.api.service.cat.CatService;
import com.snp.takealook.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class CatRecommendationController {

    private final S3Uploader s3Uploader;
    private final CatService catService;

    @PostMapping(value = "/user/{userId}/cat/face-identify", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDTO.AiImgResponse getImgAndPoints(@RequestPart(value = "image") MultipartFile file) throws IOException {
        String orgImg = s3Uploader.upload(file, "catImg");

        JSONObject response = flaskSendImg(orgImg);

        return new ResponseDTO.AiImgResponse(response.get("url").toString(),
                                            orgImg,
                                            new CatDTO.CatPoint(Double.parseDouble(response.get("leftEarX").toString()), Double.parseDouble(response.get("leftEarY").toString()),
                                                    Double.parseDouble(response.get("rightEarX").toString()), Double.parseDouble(response.get("rightEarY").toString()),
                                                    Double.parseDouble(response.get("leftEyeX").toString()), Double.parseDouble(response.get("leftEyeY").toString()),
                                                    Double.parseDouble(response.get("rightEarX").toString()), Double.parseDouble(response.get("rightEarY").toString())));
    }

    public static JSONObject flaskSendImg(String orgImg) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("orgImg", orgImg);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(data, headers);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add((req, body, execution) -> {
            ClientHttpResponse response = execution.execute(req, body);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response;
        });

        ResponseEntity<JSONObject> response = restTemplate.postForEntity(
                "http://3.39.10.151:5000/cat/face-identify",
                request,
                JSONObject.class
        );

        JSONObject responseBody = response.getBody();
        headers.clear();

        return responseBody;
    }

    @PostMapping(value = "/user/{userId}/cat/recommendation", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public List<ResponseDTO.CatRecommendListResponse> getCatRecommendList(@AuthenticationPrincipal PrincipalDetails principal,
                                                                     @RequestPart(value = "catImgUrl") String url,
                                                                     @RequestPart(value = "catPattern") String pattern,
                                                                     @RequestPart(value = "catPoints") CatDTO.CatPoint pDto,
                                                                     @RequestPart(value = "catLoc") CatDTO.Location lDto) {
        url = url.substring(1, url.length() - 1);
        pattern = pattern.substring(1, pattern.length() - 1);

        User user = principal.getUser();
        Set<Cat> catEntitySet = catService.findRecommendCats(user.getId(), pattern, lDto.getLatitude(), lDto.getLongitude());

        if (catEntitySet.size() == 0) {
            return new ArrayList<>();
        }

        JSONObject response = flaskSendCatListAndPoints(url, catEntitySet, pDto);

        Map<String, Double> sortedMap = (Map<String, Double>) response.get("sortedDict");

        LinkedHashMap<Cat, Long> catNscore = new LinkedHashMap<>();
        for (String str : sortedMap.keySet()) {
            Long id = Long.parseLong(str);
            for (Cat cat : catEntitySet) {
                if (Objects.equals(cat.getId(), id)) {
                    catNscore.put(cat, Math.round(sortedMap.get(str)));
                }
            }
        }

        List<ResponseDTO.CatRecommendListResponse> result = new ArrayList<>();
        for (Cat cat : catNscore.keySet()) {
            List<CatLocation> recentLocationList = new ArrayList<>();
            List<Selection> sameCatSelectionList = cat.getSelectionList();
            for (Selection selection : sameCatSelectionList) {
                recentLocationList.addAll(selection.getCatLocationList());
            }

            result.add(new ResponseDTO.CatRecommendListResponse(cat, recentLocationList.stream().map(ResponseDTO.CatLocationResponse::new).collect(Collectors.toList()), catNscore.get(cat)));
        }

        return result;
    }

    public static JSONObject flaskSendCatListAndPoints(String url, Set<Cat> catEntitySet, CatDTO.CatPoint points) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "multipart/form-data");

        List<ResponseDTO.RecommendedCatInfo> list = new ArrayList<>();
        for (Cat cat : catEntitySet) {
            list.add(new ResponseDTO.RecommendedCatInfo(cat));
        }

        MultiValueMap<String, Object> data = new LinkedMultiValueMap<>();
        data.add("url", url);
        data.add("catList", list);

        data.add("leftEarX", points.getLeftEarX());
        data.add("leftEarY", points.getLeftEarY());
        data.add("rightEarX", points.getRightEarX());
        data.add("rightEarY", points.getRightEarY());
        data.add("leftEyeX", points.getLeftEyeX());
        data.add("leftEyeY", points.getLeftEyeY());
        data.add("rightEyeX", points.getRightEyeX());
        data.add("rightEyeY", points.getRightEyeY());

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(data, headers);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add((req, body, execution) -> {
            ClientHttpResponse response = execution.execute(req, body);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response;
        });

        ResponseEntity<JSONObject> response = restTemplate.postForEntity(
                "http://3.39.10.151:5000/cat/similarity-scoring",
                request,
                JSONObject.class
        );

        JSONObject responseBody = response.getBody();
        headers.clear();

        return responseBody;
    }
}
