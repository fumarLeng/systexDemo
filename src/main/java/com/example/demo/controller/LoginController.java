package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession httpSession;

    @GetMapping({"/", "/login"})
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        if (userService.checkUser(username, password)) {
            httpSession.setAttribute("username", username);
            model.addAttribute("username", username);
            return "loginSuccess";
        } else {
            model.addAttribute("error", "密碼錯誤");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        httpSession.invalidate();
        model.addAttribute("msg", "您已成功登出");
        return "login";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(Model model) {
//        String username = httpSession.getAttribute("username").toString();
//        if (username== null) {
//            model.addAttribute("error", "請先登入");
//            return "login";
//        }
//        model.addAttribute("username",username);
        return "loginSuccess";
    }


}


