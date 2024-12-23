package org.com.springsecuritydemo.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {

    @GetMapping("/")
    public String home(HttpServletRequest request){
        return "Hello World  " + request.getSession().getId();
    }
}
