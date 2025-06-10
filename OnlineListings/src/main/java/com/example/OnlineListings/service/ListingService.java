package com.example.OnlineListings.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.OnlineListings.model.Listing;
import com.example.OnlineListings.repository.ListingRepository;
import com.example.OnlineListings.specification.ListingSpecification;

@Service
public class ListingService {

	@Autowired
	private ListingRepository repository;
	
	public Page<Listing> findAllWithFilters(String category, String keyword, Double minPrice, Double maxPrice, String owner, int page) {
	    Pageable pageable = PageRequest.of(page - 1, 20, Sort.by(Sort.Order.desc("listedAt")));
	    Specification<Listing> spec = ListingSpecification.withFilters(category, keyword, minPrice, maxPrice, owner);
	    return repository.findAll(spec, pageable);
	}


}
