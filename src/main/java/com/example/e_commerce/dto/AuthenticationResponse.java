package com.example.e_commerce.dto;

import lombok.Data;

@Data
public class AuthenticationResponse {

    private String token;

    public AuthenticationResponse(String jwt) {
    }
}
