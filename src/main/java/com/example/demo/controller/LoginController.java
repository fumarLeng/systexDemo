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

    @GetMapping({"/", "/login"})
    public String login(HttpSession session, Model model) {
        if(session.getAttribute("error") != null) {
            model.addAttribute("error", session.getAttribute("error"));
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model , HttpSession httpSession) {
            return "loginSuccess";
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpSession httpSession) {
        httpSession.invalidate();
        model.addAttribute("msg", "您已成功登出");
        return "login";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(Model model , HttpSession httpSession) {
        Users user =  (Users)httpSession.getAttribute("user");
        model.addAttribute("user", user);
        return "loginSuccess";
    }


}


