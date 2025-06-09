package com.example.OnlineListings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.OnlineListings.model.Listing;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Integer> {

}
