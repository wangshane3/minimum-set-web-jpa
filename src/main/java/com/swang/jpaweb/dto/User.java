package com.swang.jpaweb.dto;

import com.swang.jpaweb.model.Role;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class User {
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String[] roles;

    public static User valueOf(final com.swang.jpaweb.model.User dao) {
        User dto = new User();
        BeanUtils.copyProperties(dao, dto);
        dto.setRoles(dao.getRoles().stream().map(Role::getRole).toArray(String[]::new));
        return dto;
    }
}