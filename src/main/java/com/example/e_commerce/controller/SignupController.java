//package com.example.e_commerce.controller;
//
//import com.example.e_commerce.dto.SignupDto;
//import com.example.e_commerce.dto.UserDto;
//import com.example.e_commerce.service.user.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RestController
//@RequiredArgsConstructor
//public class SignupController {
//
//    private UserService userService;
//
//    @PostMapping("/sign-up")
//    public ResponseEntity<?> signUser(@RequestBody SignupDto signupDto) {
//        UserDto createdUser = userService.createUser(signupDto);
//if (createdUser == null) {
//    return new ResponseEntity<>("User not created. Come again letter!", HttpStatus.BAD_REQUEST);
//}
//    return new ResponseEntity<>("User created!", HttpStatus.CREATED);
//    }
//}

package com.example.e_commerce.controller;

import com.example.e_commerce.dto.SignupDto;
import com.example.e_commerce.dto.UserDto;
import com.example.e_commerce.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/sign-up")
public class SignupController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUser(@RequestBody SignupDto signupDto) {

        if (userService.hasUserWithEmail(signupDto.getEmail())) {
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto createdUser = userService.createUser(signupDto);
        if (createdUser == null) {
            return new ResponseEntity<>("User not created. Come again later!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User created!", HttpStatus.CREATED);
    }
}
