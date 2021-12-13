package com.snp.takealook.api.controller;

import com.snp.takealook.api.service.user.Social;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

//    private final HttpSession httpSession;

//    @GetMapping("/test/login")
//    public String getCode() {
//
//        return "login";
//    }

//    @PostMapping("https://kauth.kakao.com/oauth/token")
//    public String getAuthKey(String code) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
//
//        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
//        data.add("grant_type", "authorization_code");
//        data.add("client_id", "3242414d0cbf0ca5fefda3c8d1548ab4");
//        data.add("redirect_uri", "http://localhost/login/oauth2/code/kakao");
//        data.add("code", code);
//
//    }

//    @GetMapping("/oauth2/authorization/kakao")
//    public void login() {
//        String code = "http://localhost/oauth2/authorization/";
//    }

//    @GetMapping("/")
//    public String index(Model model) {
//
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
//
//        if (user != null) {
//            System.out.println(user.getNickname()+ "-------------------");
//            model.addAttribute("userName1", user.getNickname());
//        }
//
//        return "index";
//    }
}
