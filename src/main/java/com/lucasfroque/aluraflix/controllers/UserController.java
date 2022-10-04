package com.lucasfroque.aluraflix.controllers;

import com.lucasfroque.aluraflix.dto.request.UserForm;
import com.lucasfroque.aluraflix.dto.response.UserDto;
import com.lucasfroque.aluraflix.entities.User;
import com.lucasfroque.aluraflix.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody @Valid UserForm userForm) {
        User user = service.create(userForm);
        UserDto userDto = new UserDto(user);
        return ResponseEntity.ok(userDto);
    }
    @GetMapping
    public ResponseEntity<Page<UserDto>> listAll(@PageableDefault(size = 10,
            direction = Sort.Direction.DESC,
            sort = "id") Pageable pageable) {

        return ResponseEntity.ok(service.listAll(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
