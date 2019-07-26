package com.beet.demo.web;

import com.beet.demo.doamin.Book;
import com.beet.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    Book configbook;

    @Autowired
    private IUserService userService;

    @GetMapping("/test")
    public String test(){
        return "Hello";
    }

    @GetMapping("/config")
    public Book book() { return configbook;}

    @GetMapping("/user")
    public String user(){
        return userService.getMessage();
    }
}