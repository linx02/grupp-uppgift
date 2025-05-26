package com.caw24g.grupp_uppgift.controllers;

import com.caw24g.grupp_uppgift.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    //Visar användarens profil
    @GetMapping
    public ResponseEntity<String> showUserPage() {

        String userProfile = "User Profile Information";
        return ResponseEntity.ok(userProfile);
    }

    //Lägger till en ny användare
//    @PostMapping
//    public ResponseEntity<String> addNewUser(User user) {
//
//        return ResponseEntity.ok("User added successfully");
//    }

    //Tar bort en användare
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser() {

        return ResponseEntity.ok("User deleted successfully");
    }

    //Uppdaterar en användares information
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser() {

        return ResponseEntity.ok("User updated successfully");
    }
}
