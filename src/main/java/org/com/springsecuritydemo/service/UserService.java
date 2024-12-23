package org.com.springsecuritydemo.service;


import org.com.springsecuritydemo.model.Users;
import org.com.springsecuritydemo.repo.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private UserRepo repo;
    private AuthenticationManager authManager;
    private JWTservice jwtService;

    public UserService(UserRepo repo, AuthenticationManager authManager, JWTservice jwtService) {
        this.repo = repo;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }


    public Users registerUser(Users user) {
        return repo.save(user);
    }


    public String verify(Users user) {

        //this will get the login data and convert it into a token
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

        if (auth.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        }
        return "fail";
    }
}
