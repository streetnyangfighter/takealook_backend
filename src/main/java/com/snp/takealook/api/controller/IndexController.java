package com.snp.takealook.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {

//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
//
//        if (user != null) {
//            System.out.println(user.getNickname()+ "-------------------");
//            model.addAttribute("userName1", user.getNickname());
//        }

        return "index";
    }
}
