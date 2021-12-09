package com.snp.takealook.api.controller;

import com.snp.takealook.config.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final HttpSession httpSession;

    @GetMapping("/login/{provider}")
    public String login(@PathVariable String provider) {
        String url = "http://localhost/oauth2/authorization/" + provider;
        return "redirect:" + url;
    }

    @GetMapping("/")
    public String index(Model model) {

        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) {
            System.out.println(user.getNickname()+ "-------------------");
            model.addAttribute("userName1", user.getNickname());
        }

        return "index";
    }
}
