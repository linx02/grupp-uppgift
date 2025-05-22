package com.caw24g.grupp_uppgift.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    //Visar alla inlägg
    @GetMapping
    public List<Post> getAllPosts() {

        return List;
    }

    //Lägger till ett nytt inlägg
    @PostMapping
    public Post addPost() {

        return post;
    }

    //Uppdaterar ett inlägg
    @PutMapping("/{id}")
    public Post updatePost() {

        return updatePost();
    }

    //Tar bort ett inlägg
    @DeleteMapping("/{id}")
    public ResponseEntity<> deletePost() {

        return ResponseEntity;
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> reviewPost() {

        return ResponseEntity;
    }
}
