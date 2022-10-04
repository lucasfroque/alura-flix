package com.lucasfroque.aluraflix.dto.request;

import com.lucasfroque.aluraflix.entities.User;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class UserForm {
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotBlank
    private String password;

    public User toUser() {
        return new User(this.name, this.email, this.password);
    }
}
