package com.example.OnlineListings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CreateUpdateListingDTO {
	private String name;
	private String description;
	private String imageUrl;
	private double price;
	private String category;
	private String owner;
	private String city;
}
