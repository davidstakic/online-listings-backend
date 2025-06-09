package com.example.OnlineListings.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.OnlineListings.dto.SignUpDTO;
import com.example.OnlineListings.model.User;
import com.example.OnlineListings.service.UserService;


@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService service;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody SignUpDTO dto) throws Exception {
		if (service.findByUsername(dto.getUsername()) != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		String encodedPassword = passwordEncoder.encode(dto.getPassword());
		
		User user = User.builder()
				.username(dto.getUsername())
				.password(encodedPassword)
				.phoneNumber(dto.getPhoneNumber())
				.registeredAt(LocalDateTime.now())
				.authorities("USER")
				.build();
		
		service.save(user);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
