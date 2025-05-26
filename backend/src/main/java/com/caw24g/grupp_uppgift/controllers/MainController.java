package com.caw24g.grupp_uppgift.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Redundant

@RestController
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String showMainPage() {
        return "home";
    }
}

