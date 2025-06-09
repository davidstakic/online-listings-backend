package com.example.OnlineListings.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.OnlineListings.dto.LoginDTO;
import com.example.OnlineListings.dto.TokenDTO;
import com.example.OnlineListings.security.TokenUtils;
import com.example.OnlineListings.service.UserDetailsServiceImpl;

@CrossOrigin
@RestController
public class AuthenticationController {

    private AuthenticationManager authenticationManager;

    private UserDetailsServiceImpl userDetailsService;

    private TokenUtils tokenUtils;

    @Autowired
    public AuthenticationController(
            AuthenticationManager authenticationManager,
            UserDetailsServiceImpl userDetailsService,
            TokenUtils tokenUtils
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenUtils = tokenUtils;
    }

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> login(@RequestBody LoginDTO login) {
        try {
            TokenDTO token = new TokenDTO();

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(login.getUsername());
            
            if (userDetails != null) {
            	this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

                String tokenValue = this.tokenUtils.generateToken(userDetails);
                token.setToken(tokenValue);

                return new ResponseEntity<>(token, HttpStatus.OK);
            }
            
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

