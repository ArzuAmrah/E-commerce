package com.example.e_commerce.controller;

import com.example.e_commerce.dto.AuthenticationRequest;
import com.example.e_commerce.entities.UserEntity;
import com.example.e_commerce.repository.UserRepository;
import com.example.e_commerce.service.user.UserService;
import com.example.e_commerce.utils.JwtUtil;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AuthhenticationController {

    private UserService userService;

    private AuthenticationManager authenticationManager;

    private UserDetailsService userDetailsService;

    private UserRepository userRepository;

    private JwtUtil jwtUtil;

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    @PostMapping("/authenticate")
    public void  createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException, JsonEOFException, ServletException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password.");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "User is not activated");
            return;
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        UserEntity user = userRepository.findFirstByEmail(authenticationRequest.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        final String jwt = jwtUtil.generateToken(authenticationRequest.getUsername());

        response.getWriter().write(new JSONPObject(TOKEN_PREFIX + jwt, userDetails).toString());

        response.addHeader("Access Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Allow-Headers", "Authorization,X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-Header");
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
    }

}



