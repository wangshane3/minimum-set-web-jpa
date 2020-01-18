package com.swang.jpaweb.repo;

import com.swang.jpaweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(@Param("email") String email);
    Optional<User> findByUsername(@Param("username") String username);
    @Query("UPDATE User u SET u.firstName = :name WHERE u.id = :userId")
    void setFirstName(@Param("userId") Integer id, @Param("name") String name);
}