package com.caw24g.grupp_uppgift.serviceTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testAddNewUser() {
        User newUser = new User(null, "Max", "max@cloud.com");
        User savedUser = new User(1L, "Max", "max@cloud.com");

        when(userRepository.save(newUser)).thenReturn(savedUser);

        User result = userService.addUser(newUser);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Max", result.getName());
        assertEquals("max@cloud.com", result.getEmail());
    }

    @Test
    void testShowAllUsers() {
        List<User> users = List.of(
                new User(1L, "Tony", "tony@cloud.com"),
                new User(2L, "Linx", "linx@cloud.com")
        );

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("Tony", result.get(0).getName());
        assertEquals("linx@cloud.com", result.get(1).getEmail());
    }
}
