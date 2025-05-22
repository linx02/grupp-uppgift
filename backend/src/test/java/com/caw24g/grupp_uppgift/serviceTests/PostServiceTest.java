package com.caw24g.grupp_uppgift.serviceTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void testAddNewPost() {
        Post newPost = new Post(null, 2L, "location", "Fint ställe", 5);
        Post savedPost = new Post(1L, 2L, "location", "Fint ställe", 5);

        when(postRepository.save(newPost)).thenReturn(savedPost);

        Post result = postService.addPost(newPost);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(2L, result.getUserId());
        assertEquals("Fint ställe", result.getReview());
        assertEquals(5, result.getStars());
    }

    @Test
    void testShowAllPosts() {
        List<Post> mockPosts = List.of(
                new Post(1L, 1L, "Örkelljunga", "Mjeaa", 3),
                new Post(2L, 2L, "Hawaii", "Awsome", 5)
        );

        when(postRepository.findAll()).thenReturn(mockPosts);

        List<Post> result = postService.getAllPosts();

        assertEquals(2, result.size());
        assertEquals("Mjeaa", result.get(0).getReview());
        assertEquals(5, result.get(1).getStars());
    }

}
