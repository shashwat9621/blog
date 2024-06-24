package com.myproject.blog.security;

import com.myproject.blog.entities.User;
import com.myproject.blog.exceptions.ResouceNotFoundException;
import com.myproject.blog.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        User user =this.userRepo.findByEmail(username).orElseThrow(()-> new ResouceNotFoundException("User","email :"+username,0));

        return user;
    }
}
