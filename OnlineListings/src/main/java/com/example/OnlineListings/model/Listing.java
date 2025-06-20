package com.example.OnlineListings.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.OnlineListings.enums.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Listing {
	@Id
	@SequenceGenerator(name = "listingSeqGen", sequenceName = "listingSeq", initialValue = 101, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "listingSeqGen")
	private int id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "image_url", nullable = false)
	private String imageUrl;
	
	@Column(name = "price", nullable = false)
	private double price;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "category", nullable = false)
	private Category category;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
	private User owner;
	
	@Column(name = "city", nullable = false)
	private String city;
	
	@Column(name = "listed_at", nullable = false)
	private LocalDateTime listedAt;
}
