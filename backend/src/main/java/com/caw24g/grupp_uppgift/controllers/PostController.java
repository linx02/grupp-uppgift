package com.caw24g.grupp_uppgift.controllers;

import com.caw24g.grupp_uppgift.models.Post;
import com.caw24g.grupp_uppgift.models.User;
import com.caw24g.grupp_uppgift.repositories.UserRepository;
import com.caw24g.grupp_uppgift.security.JwtUtil;
import com.caw24g.grupp_uppgift.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public PostController(PostService postService, JwtUtil jwtUtil, UserRepository userRepository) {
        this.postService = postService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    //hämta alla inlägg
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // hämta ett specifikt inlägg
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable int id) {
        return postService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //skapa inlägg med bild
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Post> addPost(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("location") String location,
            @RequestParam("rating") int rating,
            @RequestParam("review") String review,
            @RequestParam("cityId") int cityId,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        try {
            //Hämta e-post från JWT-token
            String token = authHeader.replace("Bearer ", "");
            String email = jwtUtil.getEmailFromToken(token);
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("Användare finns inte"));

            //Skapa inlägg
            Post post = postService.createPost(user.getId(), location, rating, review, imageFile, cityId);
            return ResponseEntity.status(HttpStatus.CREATED).body(post);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
