package com.caw24g.grupp_uppgift.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    //Visar användarens profil
    @GetMapping
    public User showUserPage() {

        return user;
    }

    //Lägger till en ny användare
    @PostMapping
    public ResponseEntity<String> addNewUser(User user) {

        return ResponseEntity;
    }

    //Tar bort en användare
    @DeleteMapping("/{id}")
    public ResponseEntity<> deleteUser() {

        return ResponseEntity;
    }

    //Uppdaterar en användares information
    @PutMapping("/{id}")
    public User updateUser() {

        return updatedUser;
    }
}
