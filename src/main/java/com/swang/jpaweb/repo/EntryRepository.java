package com.swang.jpaweb.repo;

import com.swang.jpaweb.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Integer> {
}