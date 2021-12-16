package com.snp.takealook.api.service.user;


import com.snp.takealook.api.dto.oauth.KakaoUserInfo;
import com.snp.takealook.api.dto.oauth.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class Social {

    public static OAuth2UserInfo kakao (String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("grant_type", "authorization_code");
        data.add("client_id", "3242414d0cbf0ca5fefda3c8d1548ab4");
        data.add("redirect_uri", "http://localhost:5500/login.html");
        data.add("code", code);
        data.add("client_secret", "BRQwYzHeGyi348mXHWDgr7qMql1Zkm89");

        HttpEntity<MultiValueMap<String, String>> kakaoRequest = new HttpEntity<>(data, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JSONObject> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoRequest,
                JSONObject.class
        );

        JSONObject responseBody = response.getBody();

        headers.clear();
        data.clear();
        headers.add("Authorization", "Bearer "+responseBody.get("access_token"));
        kakaoRequest = new HttpEntity<>(data, headers);
        response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoRequest,
                JSONObject.class
        );

        OAuth2UserInfo OAuth2UserInfo = new KakaoUserInfo(response.getBody());


        return OAuth2UserInfo;
    }
}
