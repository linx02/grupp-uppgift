package com.caw24g.grupp_uppgift.controllers;

import com.caw24g.grupp_uppgift.models.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    //Visar alla inlägg
    @GetMapping
    public ResponseEntity<List<String>> getAllPosts() {

        List<String> posts = List.of("Post 1", "Post 2", "Post 3");
        return ResponseEntity.ok(posts);
    }

    // Finns 2 tillvägagångssätt här, antingen skickar jag bilden som base64 ihop
    // med resterande json eller så måste Controllern ta emot multipart/form-data
    //Lägger till ett nytt inlägg
    @PostMapping
    public ResponseEntity<String> addPost() {

        return ResponseEntity.ok("Post added successfully");
    }

    // Vi kan låta bli den här funktionaliteten för enkelhetens skull?
    //Uppdaterar ett inlägg
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost() {

        return ResponseEntity.ok("Post updated successfully");
    }

    //Tar bort ett inlägg
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost() {

        return ResponseEntity.ok("Post deleted successfully");
    }

    // Vad är denna till för?
    @PostMapping("/{id}")
    public ResponseEntity<String> reviewPost() {

        return ResponseEntity.ok("Post reviewed successfully");
    }
}
