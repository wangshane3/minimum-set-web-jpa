package com.swang.jpaweb.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swang.jpaweb.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitle(String title);
}