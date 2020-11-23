package com.example.demo.controller;


import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String getUsers(Model model){
        Iterable<User> users = userService.findAll();
        model.addAttribute("users", users);
        //return users;
        return "home";
    }

    @GetMapping("/create")
    public String createUserForm(Model model){
        model.addAttribute("user", new User());
        return "create";
    }


    @GetMapping("/users/search")
    public String searchForm(@RequestParam(value = "name", defaultValue = "") String name, Model model){
        List<User> users = userService.search(name);
        model.addAttribute("users", users);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth);
        return "search";
    }

    @GetMapping("/users/{id}/edit")
    public String show(@PathVariable("id") Integer id, Model model){
        model.addAttribute("user", userService.findOne(id));

        return "show";
    }

    @PutMapping("/users/{id}/edit")
    public String show(@Valid @RequestBody User user, BindingResult result, RedirectAttributes redirect){
        if (result.hasErrors()) {
            return "form";
        }

        userService.save(user);
        redirect.addFlashAttribute("successMessage", "Saved user successfully!");

        return "redirect:/home";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id") Integer id){
        userService.delete(id);
        return "redirect:/home";
    }

    @PostMapping("/doCreate")
    public String createUserForm(@Valid User user, BindingResult result, RedirectAttributes redirect){
        if (result.hasErrors()) {
            return "form";
        }
        userService.save(user);
        redirect.addFlashAttribute("successMessage", "Saved user successfully!");
        return "redirect:/create";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
}
