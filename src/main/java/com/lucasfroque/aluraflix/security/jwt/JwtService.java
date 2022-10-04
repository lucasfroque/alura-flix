package com.lucasfroque.aluraflix.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lucasfroque.aluraflix.entities.User;
import com.lucasfroque.aluraflix.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;
    @Autowired
    private UserRepository userRepository;

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + expiration);
        return JWT.create()
                .withIssuer("API Aluraflix")
                .withSubject(user.getEmail())
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }
    public boolean validateToken(String token){
        try{
            JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    public Optional<User> getUser(String token) {
        String username = JWT.require(Algorithm.HMAC256(secret)).build().verify(token).getSubject();
        return userRepository.findByEmail(username);
    }
    public String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }
    public boolean isValid(String token) {
        if (token != null && validateToken(token)) {
            Optional<User> user = getUser(token);
            return user.isPresent();
        }
        return false;
    }
}
