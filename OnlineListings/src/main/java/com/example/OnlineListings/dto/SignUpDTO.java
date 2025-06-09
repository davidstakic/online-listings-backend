package com.example.OnlineListings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDTO {
	private String username;
	private String password;
	private String phoneNumber;
}
