package com.swang.jpaweb.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity // DAO
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;
}