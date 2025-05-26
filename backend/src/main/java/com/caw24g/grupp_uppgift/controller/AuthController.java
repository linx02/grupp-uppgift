package com.caw24g.grupp_uppgift.controller;


import com.caw24g.grupp_uppgift.models.User;
import com.caw24g.grupp_uppgift.repositories.UserRepository;
import com.caw24g.grupp_uppgift.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    // Injectar JwtUtil för att hantera tokens
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        String password = data.get("password");

        Optional<User> userOpt = userRepository.findByEmail(email);

        // Kontrollera om användaren finns och lösenordet stämmer
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            String token = jwtUtil.generateToken(email); // Generera token för användare
            // Returnera att allt lyckats
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "email", email,
                    "success", true
            ));
        }
        return ResponseEntity.badRequest().body(Map.of("error", "Fel inloggning")); // Användaren finns inte eller fel lösenord
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        String password = data.get("password");

        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Användaren finns redan"));
        }
        // Skapa och spara ny användare
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setName(data.get("name") != null ? data.get("name") : "Användare");

        userRepository.save(newUser);
        String token = jwtUtil.generateToken(email);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "email", email,
                "success", true
        ));
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validate(@RequestHeader("Authorization") String auth) {
        try {
            String token = auth.replace("Bearer ", "");
            // Validera token med JwtUtil - ingen databas-åtkomst behövs här All nödvändig info finns redan i token
            String email = jwtUtil.validateToken(token);
            return ResponseEntity.ok(Map.of("email", email, "valid", true));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Ogiltig token"));
        }
    }
}
