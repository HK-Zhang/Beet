package com.beet.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Value("${user.userName}")
    private String userName;

    @Value("${user.sex}")
    private String sex;

    @Value("${user.age}")
    private String age;

    @Override
    public String getMessage() {
        return "Name:"+userName+" Sex:"+sex+" Age:"+age;
    }
}
