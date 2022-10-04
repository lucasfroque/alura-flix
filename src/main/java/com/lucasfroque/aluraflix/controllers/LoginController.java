package com.lucasfroque.aluraflix.controllers;

import com.lucasfroque.aluraflix.dto.request.LoginForm;
import com.lucasfroque.aluraflix.dto.response.TokenDto;
import com.lucasfroque.aluraflix.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtService jwtService;

    @PostMapping
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginForm loginForm) {
        try{
            Authentication authentication = authManager.authenticate(loginForm.toAuthentication());
            TokenDto tokenDto = new TokenDto(jwtService.generateToken(authentication), "Bearer");
            return ResponseEntity.ok(tokenDto);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

}
