package com.caw24g.grupp_uppgift.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;

    private String location;

    private String review;

    private int rating;

    private String imageUrl;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Post(String location, String review, int rating) {
        this.location = location;
        this.review = review;
        this.rating = rating;
    }



    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setUser(User user) {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
