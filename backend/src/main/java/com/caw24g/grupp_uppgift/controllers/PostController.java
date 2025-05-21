package com.caw24g.grupp_uppgift.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {


    @GetMapping("/showPosts")
    public String getStartPage() {

        return "posts";
    }


    @PostMapping("/addPost")
    public String addPost() {

        return "posts";
    }


    @PostMapping("/updatePost")
    public String updatePost() {

        return "posts";
    }

    @PostMapping("/deletePost")
    public String deletePost() {

        return "posts";
    }

    @PostMapping("/reviewPost")
    public String reviewPost() {

        return "posts";
    }
}
