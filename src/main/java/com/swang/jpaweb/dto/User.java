package com.swang.jpaweb.dto;

import com.swang.jpaweb.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data @AllArgsConstructor @NoArgsConstructor
public class User {
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String[] roles;

    public static User valueOf(final com.swang.jpaweb.model.User dao) {
        User dto = new User();
        BeanUtils.copyProperties(dao, dto, null, "roles");
        dto.setRoles(dao.getRoles().stream().map(Role::getRole).toArray(String[]::new));
        return dto;
    }
}