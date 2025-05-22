package com.caw24g.grupp_uppgift.repositories;


import com.caw24g.grupp_uppgift.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
}
