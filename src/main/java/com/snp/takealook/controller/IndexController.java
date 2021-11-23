package com.snp.takealook.controller;

import com.snp.takealook.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class IndexController {
    private final HttpSession httpSession;

//    @GetMapping("/")
//    public void index(Model model) {
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
//
//        if (user != null) {
//            model.addAttribute("userName1", user.getNickname());
//        }
//
////        return "index??";
//    }
}
