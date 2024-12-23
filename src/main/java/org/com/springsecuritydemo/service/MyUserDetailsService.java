package org.com.springsecuritydemo.service;

import org.com.springsecuritydemo.model.UserPrinciple;
import org.com.springsecuritydemo.model.Users;
import org.com.springsecuritydemo.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {


    //it provide the service to the authentication provider to get access of all user from the database
    private UserRepo repo;

    public MyUserDetailsService(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = repo.findByUsername(username);

        if(user == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException(username);
        }


        return new UserPrinciple(user);
    }
}
