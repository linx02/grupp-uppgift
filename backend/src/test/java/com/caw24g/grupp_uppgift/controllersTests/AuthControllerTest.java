package com.caw24g.grupp_uppgift.controllersTests;

import com.caw24g.grupp_uppgift.controllers.AuthController;
import com.caw24g.grupp_uppgift.models.User;
import com.caw24g.grupp_uppgift.repositories.UserRepository;
import com.caw24g.grupp_uppgift.security.JwtUtil;
import com.caw24g.grupp_uppgift.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//Laddar auth controller och dess beroenden
@WebMvcTest(AuthController.class)

@Import({AuthControllerTest.MockConfig.class, SecurityConfig.class})
public class AuthControllerTest {

    //MockMvc för att testa HTTP-anrop
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    //Mockar beroenden för AuthController
    @TestConfiguration
    public static class MockConfig {
        @Bean
        public UserRepository userRepository() {
            return mock(UserRepository.class);
        }

        @Bean
        public JwtUtil jwtUtil() {
            return mock(JwtUtil.class);
        }
    }

    // Testar inloggning med giltiga uppgifter
    @Test
    public void loginSuccessTest() throws Exception {

        // Skapar en mock användare och simulerar att den finns i databasen
        String email = "test@example.com";
        String password = "pass";
        String token = "token";

        // Skapar en mock-användare med e-post och lösenord
        User mockUser = new User();
        mockUser.setEmail(email);
        mockUser.setPassword(password);
        mockUser.setName("Test");

        // Simulerar att användaren finns i databasen
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
        when(jwtUtil.generateToken(email)).thenReturn(token);

        // Gör en POST förfrågan till inloggnings api
        mockMvc.perform(post("/api/auth/login")

                        // GÖr om det till en JSON POST
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "test@example.com",
                                    "password": "pass"
                                }
                                """))

                // Förvänta sig rätt svar från servern, token, e-post och success
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.success").value(true));
    }
    // Testar inloggning med felaktiga uppgifter
    @Test
    public void loginFailureTest() throws Exception {

        // Simulera att användaren inte finns
        when(userRepository.findByEmail("fel@example.com")).thenReturn(Optional.empty());

        // Gör en POST förfrågan till inloggnings api
        mockMvc.perform(post("/api/auth/login")

                        // GÖr om det till en JSON POST
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "fel@example.com"
                                }
                                """))

                // Förvänta att servern svarar med Bad Request
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Fel inloggning"));
    }
    // Testar lyckad registrering av en ny användare
    @Test
    public void registerSuccessTest() throws Exception {

        // Skapar en mock användare utan token
        String email = "daniel@cloud.se";
        String token = "token";

        // Skapar en mock-användare med e-post och lösenord
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(jwtUtil.generateToken(email)).thenReturn(token);

        // Gör en POST förfrågan till registrerings api
        mockMvc.perform(post("/api/auth/register")

                        // GÖr om det till en JSON POST
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "daniel@cloud.se",
                                    "password": "pass"
                                }
                                """))

                // Förvänta sig rätt svar från servern, token, e-post och success
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.success").value(true));

        // Verifiera att användaren sparades i databasen
        verify(userRepository, times(1)).save(any(User.class));
    }

    // Testar registrering med en användare som redan finns
    @Test
    public void userExistsRegisterFailTest() throws Exception {

        // Skapar en mock användare som redan finns i databasen
        String email = "existing@example.com";

        // Simulerar att användaren redan finns i databasen
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        // Gör en POST förfrågan till registrerings api
        mockMvc.perform(post("/api/auth/register")

                        // GÖr om det till en JSON POST
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "existing@example.com",
                                    "password": "password123"
                                }
                                """))

                // Förvänta att servern svarar med Bad Request
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Användaren finns redan"));

    }

    // Testar lyckad validering av token
    @Test
    public void validateSuccessTest() throws Exception {

        // Skapar en mock användare och simulerar att den finns i databasen
        String email = "tony@cloud.se";
        String token = "validToken";

        // Simulerar att token är giltig och returnerar e-post
        when(jwtUtil.validateToken(token)).thenReturn(email);

        // Gör en GET-förfrågan till validerings api
        mockMvc.perform(get("/api/auth/validate")

                        // Lägger till token
                        .header("Authorization", "Bearer " + token))

                // Förvänta sig rätt svar från servern, e-post och valid
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.valid").value(true));
    }

    // Testar validering av token som är ogiltig
    @Test
    public void validateFailTest() throws Exception {

        // Skapar en ogiltig token
        String token = "invalidToken";

        // Simulerar att token är ogiltig och kastar ett undantag
        when(jwtUtil.validateToken(token)).thenThrow(new RuntimeException("Ogiltig token"));

        // Gör en GET-förfrågan till validerings api
        mockMvc.perform(get("/api/auth/validate")

                        // Lägger till token
                        .header("Authorization", "Bearer " + token))

                // Förvänta att servern svarar med Bad Request
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Ogiltig token"));
    }
}
