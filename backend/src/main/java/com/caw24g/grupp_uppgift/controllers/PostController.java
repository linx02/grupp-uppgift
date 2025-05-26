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

    //Lägger till ett nytt inlägg
    @PostMapping
    public ResponseEntity<String> addPost() {

        return ResponseEntity.ok("Post added successfully");
    }

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

    @PostMapping("/{id}")
    public ResponseEntity<String> reviewPost() {

        return ResponseEntity.ok("Post reviewed successfully");
    }
}
