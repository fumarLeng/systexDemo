package com.example.demo.service;

import com.example.demo.model.Users;
import com.example.demo.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Users checkUser(String username, String password) {
        Users user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public Integer addUser(Users user) {
        return userRepository.save(user).getId();
    }

    public boolean hasUser(String username) {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }
        return true;
    }


}