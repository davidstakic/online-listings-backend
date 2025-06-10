package com.example.OnlineListings.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.OnlineListings.dto.ListingDTO;
import com.example.OnlineListings.model.Listing;
import com.example.OnlineListings.service.ListingService;

@CrossOrigin
@RestController
@RequestMapping("/api/listings")
public class ListingController {

	@Autowired
	private ListingService service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getAll(
	    @RequestParam(value = "category", required = false) String category,
	    @RequestParam(value = "keyword", required = false) String keyword,
	    @RequestParam(value = "minPrice", required = false) Double minPrice,
	    @RequestParam(value = "maxPrice", required = false) Double maxPrice,
	    @RequestParam(value = "owner", required = false) String owner,
	    @RequestParam(value = "page", required = false, defaultValue = "1") int page
	) {
	    Page<Listing> listings = service.findAllWithFilters(category, keyword, minPrice, maxPrice, owner, page);

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

}
