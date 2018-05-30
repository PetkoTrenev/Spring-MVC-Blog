package com.github.petkotrenev.controllers;

import com.github.petkotrenev.entities.CustomUserDetails;
import com.github.petkotrenev.entities.Post;
import com.github.petkotrenev.services.CustomUserDetailsService;
import com.github.petkotrenev.services.PostService;
import com.github.petkotrenev.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller(value = "/blog")
public class BlogController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String posts(ModelMap modelMap) {
        List<Post> posts = postService.findAll();
        modelMap.put("posts", posts);
        return "index";
    }

    //TODO
    @GetMapping(value="/create")
    public String showNewPostPage(@AuthenticationPrincipal CustomUserDetailsService userDetails) {

        return "create_post";
    }

    @PostMapping
    public String publishPost(@RequestBody Post post, @AuthenticationPrincipal CustomUserDetails userDetails) {
        // Retrieve logged in user
//        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        post.setCreator(userService.findById(userDetails.getId()));

        postService.create(post, userService.findByUsername(userDetails.getUsername()));
        return "index";
    }


}
