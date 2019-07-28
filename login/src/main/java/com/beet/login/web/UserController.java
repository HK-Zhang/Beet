package com.beet.login.web;

import com.beet.login.domain.UserRepository;
import com.beet.login.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/add")
    public @ResponseBody String addNewUser(@RequestParam String name,@RequestParam String email
            ,@RequestParam String password, User user){
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        log.info(user.toString()+" saved to the repo");
        return "Saved";
    }

    @GetMapping(path = "/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model){
        List<User> users = userRepository.findByEmail(email);

        if(users==null){
            log.warn("attempting to log in with the non-existed account");
            return "the user not exist";
        } else {
            User user = users.get(0);
            if(user.getPassword().equals(password)){
                model.addAttribute("name",user.getName());
                log.warn(user.toString() + " logged in");
            }else{
                model.addAttribute("name","logging failed");
                log.warn(user.toString()+" failed to log in");
            }

            return "index";
        }
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> GetAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/")
    public String welcomePage(@RequestParam(name="name",required = false,defaultValue = "World") String name){
        return "index";
    }

}
