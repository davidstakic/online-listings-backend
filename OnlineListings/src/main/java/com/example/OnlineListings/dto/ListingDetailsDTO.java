package com.example.OnlineListings.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ListingDetailsDTO {
	private String name;
	private String description;
	private String imageUrl;
	private double price;
	private String category;
	private String ownerUsername;
	private String ownerPhone;
	private LocalDateTime listedAt;
}
