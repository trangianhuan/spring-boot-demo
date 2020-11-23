package com.example.demo.controller.api;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController("ApiUserController")
@RequestMapping("api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("users/{id}")
    public HashMap<String, Object> show(@PathVariable("id") Integer userId){
        Optional<User> user = userService.findOne(userId);
        User u = user.get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", u.getName());
        map.put("email", u.getEmail());
        map.put("priciple", auth.getPrincipal());
        return map;
    }
}
