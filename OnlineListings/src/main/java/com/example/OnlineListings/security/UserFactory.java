package com.example.OnlineListings.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.example.OnlineListings.model.User;

import java.util.Collection;

public class UserFactory {

    public static SecurityUser create(User user) {
        Collection<? extends GrantedAuthority> authorities;
        try {
            authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities());
        } catch (Exception e) {
            authorities = null;
        }

        return new SecurityUser(
        		user.getId(),
        		user.getUsername(),
        		user.getPassword(),
        		authorities
        );
    }


}
