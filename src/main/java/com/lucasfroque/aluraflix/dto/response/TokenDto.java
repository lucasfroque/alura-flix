package com.lucasfroque.aluraflix.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenDto {
        private String token;
        private String type;
}
