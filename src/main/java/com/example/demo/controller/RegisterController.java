package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;


    @GetMapping("/register")
    public String toRegister(Model model) {
        model.addAttribute("users", new Users());
        return "register";
    }

    @PostMapping("/register/add")
    public String register(@ModelAttribute("users") Users user, Model model) {
        if (userService.hasUser(user.getUsername())) {
            model.addAttribute("error", "使用者已存在");
            return "register";
        }

        if (userService.addUser(user) != 0) {
            model.addAttribute("msg", "新增會員成功請登入");
            return "/login";
        } else {
            model.addAttribute("error", "新增會員失敗請重新輸入");
            return "redirect:/login";
        }
    }
}
