package com.beet.first.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * HelloController
 */
@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Greeting from Spring Boot!";
    }
    
}