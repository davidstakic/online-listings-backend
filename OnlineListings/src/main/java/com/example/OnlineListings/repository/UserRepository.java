package com.example.OnlineListings.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.OnlineListings.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {


}
