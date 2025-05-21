package com.caw24g.grupp_uppgift.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @GetMapping("/user")
    public String showUserPage() {

        return "user";
    }

    @PostMapping("/addUser")
    public String addNewUser() {

        return "user";
    }

    @PostMapping("/deleteUser")
    public String deleteUser() {

        return "home";
    }

    @PostMapping("/updateUser")
    public String updateUser() {

        return "user";
    }
}
