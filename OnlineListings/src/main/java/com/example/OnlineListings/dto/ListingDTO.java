package com.example.OnlineListings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ListingDTO {
	private int id;
	private String name;
	private double price;
	private String city;
	private String category;
	private String imageUrl;
	private String owner;
}
