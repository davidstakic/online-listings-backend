package com.example.OnlineListings.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.OnlineListings.model.User;
import com.example.OnlineListings.repository.UserRepository;
import com.example.OnlineListings.security.UserFactory;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = this.userRepository.findByUsername(email);
        
        if (user == null)
        	return null;

        return UserFactory.create(user);
    }

}
