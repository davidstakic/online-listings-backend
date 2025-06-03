package com.example.OnlineListings.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.OnlineListings.model.Listing;

public interface ListingRepository extends JpaRepository<Listing, Integer> {

}
