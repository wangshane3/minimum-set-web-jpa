package com.swang.jpaweb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Collection;

@JsonIgnoreProperties({"id", "roles", "entries"}) // when used as DTO
@Data
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    private String password;

    @Column(nullable = false, unique = true)
    @Length(min = 5, message = "*Your username must have at least 5 characters")
    private String username;

    private String firstName;
    private String lastName;
    private int active;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, mappedBy = "user")
    private Collection<Entry> entries;

    public void copyIfNotNUll(final com.swang.jpaweb.dto.User dto, PasswordEncoder encoder) {
        // Encode plaintext password if changing
        if (dto.getPassword() != null) setPassword(encoder.encode(dto.getPassword()));
        if (dto.getFirstName() != null) setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) setLastName(dto.getLastName());
        if (dto.getUsername() != null) setUsername(dto.getUsername());
        if (dto.getEmail() != null) setEmail(dto.getEmail());
    }
}