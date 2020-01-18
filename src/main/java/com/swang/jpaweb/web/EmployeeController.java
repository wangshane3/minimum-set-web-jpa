package com.swang.jpaweb.web;

import com.swang.jpaweb.model.Employee;
import com.swang.jpaweb.repo.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addEmployee(@RequestBody Employee employee) {
        employeeRepository.save(employee);
    }

    @GetMapping
    public List<Employee> getEmployees(Principal user) {
        List<Employee> all = employeeRepository.findAll();
        if (user != null) {
            Employee login = new Employee();
            login.setName("principal" + user.getName());
            login.setId(all.size() + 2);
            all.add(login);
        }
        Employee login = new Employee();
        login.setName("holder" + getUsername());
        login.setId(all.size() + 3);
        all.add(login);
        return all;
    }

    // another way to get logged-in user name is in LoginController: ask Spring to inject Principal
    private String getUsername() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        log.info("user login is " + username);
        return username;
    }
}