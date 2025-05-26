package com.caw24g.grupp_uppgift.service;

import com.caw24g.grupp_uppgift.models.City;
import com.caw24g.grupp_uppgift.models.Post;
import com.caw24g.grupp_uppgift.models.User;
import com.caw24g.grupp_uppgift.repositories.PostRepository;
import com.caw24g.grupp_uppgift.repositories.UserRepository;
import com.caw24g.grupp_uppgift.repositories.CityRepository;
import com.caw24g.grupp_uppgift.service.S3Service;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final S3Service s3Service;


    public PostService(PostRepository postRepository, UserRepository userRepository, CityRepository cityRepository, S3Service s3Service) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.s3Service = s3Service;
    }

    // Denna måste antingen ta filen som base64, eller så måste Controllern uppdateras
    @Transactional
    public Post createPost(int userId, String location, int rating, String review, MultipartFile imageFile, int cityId) {
        validateRating(rating);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Användare finns inte"));

        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                imageUrl = s3Service.uploadFile(
                        imageFile.getOriginalFilename(),
                        imageFile.getInputStream(),
                        imageFile.getContentType());
            } catch (IOException e) {
                throw new RuntimeException("Misslyckades att ladda upp bild till S3", e);
            }
        }

        if (cityId <= 0) {
            throw new IllegalArgumentException("Ogiltigt cityId: " + cityId);
        }

        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new IllegalArgumentException("Stad med ID " + cityId + " finns inte."));

        Post post = new Post();
        post.setUser(user);
        post.setLocation(location);
        post.setRating(rating);
        post.setReview(review);
        post.setImageUrl(imageUrl);
        post.setCity(city);

        return postRepository.save(post);
    }

    // Vi kan låta bli den här funktionaliteten för enkelhetens skull?
    @Transactional
    public Post updatePost(int postId, String location, int rating, String review, MultipartFile imageFile) {
        validateRating(rating);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Inlägg med ID " + postId + " finns inte."));

        post.setLocation(location);
        post.setRating(rating);
        post.setReview(review);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String imageUrl = s3Service.uploadFile(
                        imageFile.getOriginalFilename(),
                        imageFile.getInputStream(),
                        imageFile.getContentType());
                post.setImageUrl(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Misslyckades att ladda upp ny bild till S3", e);
            }
        }

        return postRepository.save(post);
    }


    @Transactional
    public void deletePost(int postId) {
        if (!postRepository.existsById(postId)) {
            throw new IllegalArgumentException("Inlägg med ID " + postId + " finns inte.");
        }

        postRepository.deleteById(postId);
    }


    private void validateRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Betyget måste vara mellan 1 och 5.");
        }


    }
}
