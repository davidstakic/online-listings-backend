package com.example.OnlineListings.controller;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.OnlineListings.dto.CreateUpdateListingDTO;
import com.example.OnlineListings.dto.ListingDTO;
import com.example.OnlineListings.dto.ListingDetailsDTO;
import com.example.OnlineListings.enums.Category;
import com.example.OnlineListings.model.Listing;
import com.example.OnlineListings.model.User;
import com.example.OnlineListings.service.ListingService;
import com.example.OnlineListings.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api/listings")
public class ListingController {

	@Autowired
	private ListingService listingService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getAll(
	    @RequestParam(value = "category", required = false) String category,
	    @RequestParam(value = "keyword", required = false) String keyword,
	    @RequestParam(value = "minPrice", required = false) Double minPrice,
	    @RequestParam(value = "maxPrice", required = false) Double maxPrice,
	    @RequestParam(value = "owner", required = false) String owner,
	    @RequestParam(value = "page", required = false, defaultValue = "1") int page
	) {
	    Page<Listing> listings = listingService.findAllWithFilters(category, keyword, minPrice, maxPrice, owner, page);

	    Collection<ListingDTO> content = listings.getContent().stream()
	        .map(listing -> ListingDTO.builder()
	            .id(listing.getId())
	            .name(listing.getName())
	            .price(listing.getPrice())
	            .city(listing.getCity())
	            .category(listing.getCategory().toString())
	            .imageUrl(listing.getImageUrl())
	            .owner(listing.getOwner().getUsername())
	            .build())
	        .collect(Collectors.toList());
	    
	    Map<String, Object> response = new HashMap<>();
	    response.put("content", content);
	    response.put("totalPages", listings.getTotalPages());

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ListingDetailsDTO> getById(@PathVariable int id) {
		Listing listing = listingService.findById(id);
		
		if (listing != null) {
			ListingDetailsDTO dto = ListingDetailsDTO.builder()
					.name(listing.getName())
					.description(listing.getDescription())
					.imageUrl(listing.getImageUrl())
					.price(listing.getPrice())
					.city(listing.getCity())
					.category(listing.getCategory().toString())
					.ownerUsername(listing.getOwner().getUsername())
					.ownerPhone(listing.getOwner().getPhoneNumber())
					.listedAt(listing.getListedAt()).build();
			
			return new ResponseEntity<>(dto, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("hasAnyAuthority('USER')")
	@GetMapping(value = "/edit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CreateUpdateListingDTO> getForEdit(@PathVariable int id) {
		Listing listing = listingService.findById(id);
		
		if (listing != null) {
			CreateUpdateListingDTO dto = CreateUpdateListingDTO.builder()
					.name(listing.getName())
					.description(listing.getDescription())
					.imageUrl(listing.getImageUrl())
					.price(listing.getPrice())
					.category(listing.getCategory().toString())
					.owner(listing.getOwner().getUsername())
					.city(listing.getCity()).build();
			
			return new ResponseEntity<>(dto, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("hasAnyAuthority('USER')")
	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody CreateUpdateListingDTO dto) {
		User owner = userService.findByUsername(dto.getOwner());
		
		Listing listing = Listing.builder()
				.name(dto.getName())
				.description(dto.getDescription())
				.imageUrl(dto.getImageUrl())
				.price(dto.getPrice())
				.category(Category.valueOf(dto.getCategory()))
				.owner(owner)
				.city(dto.getCity())
				.listedAt(LocalDateTime.now())
				.build();
		
		listingService.save(listing);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyAuthority('USER')")
	@PutMapping(value = "/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> edit(@RequestBody CreateUpdateListingDTO dto, @PathVariable int id) {
		Listing listing = listingService.findById(id);
		
		if (listing != null) {
			User owner = userService.findByUsername(dto.getOwner());
			
			listing.setName(dto.getName());
			listing.setDescription(dto.getDescription());
			listing.setImageUrl(dto.getImageUrl());
			listing.setPrice(dto.getPrice());
			listing.setCategory(Category.valueOf(dto.getCategory()));
			listing.setOwner(owner);
			listing.setCity(dto.getCity());
			
			listingService.save(listing);
			
			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("hasAnyAuthority('USER')")
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		Listing listing = listingService.findById(id);
		
		if (listing != null) {
			listingService.delete(listing);
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
}
