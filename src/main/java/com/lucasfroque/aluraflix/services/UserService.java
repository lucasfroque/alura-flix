package com.lucasfroque.aluraflix.services;

import com.lucasfroque.aluraflix.dto.request.UserForm;
import com.lucasfroque.aluraflix.dto.response.UserDto;
import com.lucasfroque.aluraflix.entities.User;
import com.lucasfroque.aluraflix.exceptions.UserNotFoundException;
import com.lucasfroque.aluraflix.respositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository repository;

    public User create(UserForm userForm) {
        User user = userForm.toUser();
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return repository.save(user);
    }
    public Page<UserDto> listAll(Pageable pageable) {
        return repository.findAll(pageable).map(UserDto::new);
    }
    public UserDto findById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return new UserDto(user);
    }
}
