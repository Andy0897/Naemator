package com.example.Naemator.security;

import com.example.Naemator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        if (userRepository.findByUsername(usernameOrEmail) != null) {
            return new MyUserDetails(userRepository.findByUsername(usernameOrEmail));
        }
        else if (userRepository.findByEmail(usernameOrEmail) != null) {
            return new MyUserDetails(userRepository.findByEmail(usernameOrEmail));
        }
        throw new UsernameNotFoundException("Could not find user");
    }

}