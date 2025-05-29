package com.caw24g.grupp_uppgift.serviceTests;

import com.caw24g.grupp_uppgift.models.Post;
import com.caw24g.grupp_uppgift.models.User;
import com.caw24g.grupp_uppgift.repositories.PostRepository;
import com.caw24g.grupp_uppgift.repositories.UserRepository;
import com.caw24g.grupp_uppgift.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

//    @Test
//    public void testAddNewPost() {
//        String location = "Göteborg";
//        int rating = 4;
//        int cityId = 1;
//        String review = "Trevligt ställe";
//
//        User user = new User();
//        user.setName("Test User");
//        user.setEmail("email@email.com");
//        user.setPassword("password123");
//
//        when(userRepository.findByEmail("email@email.com")).thenReturn(Optional.of(user));
//        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        Post result = postService.createPost(userId, location, rating, review);
//
//        assertNotNull(result);
//        assertEquals(location, result.getLocation());
//        assertEquals(rating, result.getRating());
//        assertEquals(review, result.getReview());
//        assertEquals(user, result.getUser());
//    }

    @Test
    public void testDeletePost() {
        int postId = 456;
        when(postRepository.existsById(postId)).thenReturn(true);

        postService.deletePost(postId);

        verify(postRepository, times(1)).deleteById(postId);
    }

//    @Test
//    public void testUpdatePost() {
//        Post existingPost = new Post();
//        existingPost.setId(1);
//        existingPost.setLocation("Örkelljunga");
//        existingPost.setReview("Mjeaa");
//        existingPost.setRating(3);
//
//        when(postRepository.findById(1)).thenReturn(Optional.of(existingPost));
//        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        Post result = postService.updatePost(1, "Örkelljunga", 4, "Väldigt fin golfbana");
//
//        assertNotNull(result);
//        assertEquals("Örkelljunga", result.getLocation());
//        assertEquals(4, result.getRating());
//        assertEquals("Väldigt fin golfbana", result.getReview());
//    }

}
