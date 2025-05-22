package com.caw24g.grupp_uppgift.service;

import com.caw24g.grupp_uppgift.models.Post;
import com.caw24g.grupp_uppgift.models.User;
import com.caw24g.grupp_uppgift.repositories.PostRepository;
import com.caw24g.grupp_uppgift.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Post createPost(int userId, String location, int rating, String review) {
        validateRating(rating);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Användare med ID " + userId + " finns inte."));

        Post post = new Post();
        post.setUser(user);
        post.setLocation(location);
        post.setRating(rating);
        post.setReview(review);

        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(int postId, String location, int rating, String review) {
        validateRating(rating);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Inlägg med ID " + postId + " finns inte."));

        post.setLocation(location);
        post.setRating(rating);
        post.setReview(review);

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
