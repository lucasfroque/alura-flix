package com.lucasfroque.aluraflix.dto.response;

import com.lucasfroque.aluraflix.entities.Roles;
import com.lucasfroque.aluraflix.entities.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Roles role;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRoles();
    }
}
