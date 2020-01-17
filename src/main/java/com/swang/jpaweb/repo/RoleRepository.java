package com.swang.jpaweb.repo;

import com.swang.jpaweb.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(@Param("role") String role);
}