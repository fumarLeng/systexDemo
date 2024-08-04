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

    @GetMapping({"/", "/login"})
    public String login(HttpSession session, Model model) {
        if (session.getAttribute("error") != null) {
            model.addAttribute("error", session.getAttribute("error"));
        }
        return "login";
    }

    @PostMapping("/login")
    public String login() {
        return "loginSuccess";
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpSession Session) {
        Session.invalidate();
        System.out.println("登出成功!!已清除Session" );
        model.addAttribute("msg", "您已成功登出");
        return "login";
    }

    @RequestMapping(value = "/loginSuccess", method = {RequestMethod.GET,RequestMethod.POST})
    public String loginSuccess(Model model, HttpSession httpSession) {
        System.out.println("進來loginSuccess了");
        Users user = (Users) httpSession.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        System.out.println("放好user了 : " + user);
        return "loginSuccess";
    }

}


