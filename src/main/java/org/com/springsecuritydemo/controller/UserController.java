package org.com.springsecuritydemo.controller;


import org.com.springsecuritydemo.model.Users;
import org.com.springsecuritydemo.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @PostMapping("/register")
    public Users registerUser(@RequestBody Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody Users user) {
       return userService.verify(user);
    }
}
