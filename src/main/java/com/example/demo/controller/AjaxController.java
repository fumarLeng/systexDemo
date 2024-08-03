package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/ajax")
public class AjaxController {

    @RequestMapping("/login")
    public ResponseEntity<?> AjaxLogin(@RequestParam String username, @RequestParam String password, HttpSession session){


        return  ResponseEntity.ok("OK已登入");
    }
}
