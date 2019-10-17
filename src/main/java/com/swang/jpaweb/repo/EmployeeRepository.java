package com.swang.jpaweb.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swang.jpaweb.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}