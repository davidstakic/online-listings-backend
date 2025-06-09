package com.example.OnlineListings.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.OnlineListings.model.User;
import com.example.OnlineListings.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
    private UserRepository repository;

    @Transactional
    public User save(User user) {
        return repository.save(user);
    }
    
    public User findByUsername(String username) {
    	return repository.findByUsername(username);
    }

}
