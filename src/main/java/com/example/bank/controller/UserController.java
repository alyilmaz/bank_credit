package com.example.bank.controller;

import com.example.bank.domain.User;
import com.example.bank.dto.UserDTO;
import com.example.bank.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    public String register(@Valid @RequestBody UserDTO user) {
        userService.registerUser(user);
        return "User registered!";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, authenticated user!";
    }
}
