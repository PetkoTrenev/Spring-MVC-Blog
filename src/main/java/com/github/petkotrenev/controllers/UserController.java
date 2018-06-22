package com.github.petkotrenev.controllers;


import com.github.petkotrenev.entities.Role;
import com.github.petkotrenev.entities.User;
import com.github.petkotrenev.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                            @RequestParam("firstname") String firstName,
                            @RequestParam("lastname") String lastName) {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ADMIN"));
        roles.add(new Role("USER"));
        userService.create(new User(username, password, firstName, lastName, roles));
        return "login";
    }

    @RequestMapping(value="/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login.html";
    }

    //TODO: Only for admin
    @RequestMapping(value = "/list")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String getUsers(ModelMap modelMap, @AuthenticationPrincipal UserDetails viewer) {
        List<User> users = userService.findAll();
        modelMap.put("users", users);
        return "list_users";
    }

}
