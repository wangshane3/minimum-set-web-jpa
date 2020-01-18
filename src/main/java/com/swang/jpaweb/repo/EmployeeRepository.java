package com.swang.jpaweb.repo;

import com.swang.jpaweb.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}