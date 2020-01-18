package com.swang.jpaweb.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity // DAO
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String author;
}