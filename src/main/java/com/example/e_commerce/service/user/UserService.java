package com.example.e_commerce.service.user;

import com.example.e_commerce.dto.SignupDto;
import com.example.e_commerce.dto.UserDto;

public interface UserService {

    UserDto createUser(SignupDto signupDto);

    boolean hasUserWithEmail(String email);
}
