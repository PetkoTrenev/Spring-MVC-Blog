package com.github.petkotrenev.controllers;

import com.github.petkotrenev.entities.CustomUserDetails;
import com.github.petkotrenev.entities.Post;
import com.github.petkotrenev.entities.User;
import com.github.petkotrenev.services.PostService;
import com.github.petkotrenev.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BlogController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping(value="/")
    public String posts(ModelMap modelMap) {
        List<Post> posts = postService.findAll();
        modelMap.put("posts", posts);
        return "index";
    }

    //TODO
    @GetMapping(value="/post/create")
    public String showNewPostPage() {
        return "create_post";
    }

    @PostMapping("/post/create")
    public String publishPost(Post post, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Post newPost = postService.create(post, userService.findByUsername(userDetails.getUsername()));
        return "redirect:/";
    }

    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable("id") Long postId, ModelMap modelMap) {
        Post post = postService.findById(postId);
        // TODO Check if exists

        modelMap.put("post", post);
        return "show_post";
    }

    @GetMapping(value="/post/edit/{id}")
    @Transactional
    public String showEditPostView(@PathVariable("id") Long id,
                                   @AuthenticationPrincipal CustomUserDetails userDetails,
                                   ModelMap modelMap) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        Post post = postService.findById(id);
        // TODO Check if exists?

        if (postService.isPostLockedForUser(post, currentUser)) {
            // TODO Make error page that says post is locked
            return "redirect:/post_locked";
        }

        post = postService.lockForUser(post, currentUser);

        modelMap.put("post", post);
        return "edit_post";
    }

    @PostMapping(value="/post/edit/{id}")
    @Transactional
    public String acceptPostEdit(@PathVariable("id") Long id,
                                 Post post,
                                 @AuthenticationPrincipal CustomUserDetails userDetails,
                                    RedirectAttributes redirectAttributes) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        Post postFromDb = postService.findById(id);

        if (postService.isPostLockedForUser(post, currentUser)) {
            // TODO Make error page that says post is locked
            redirectAttributes.addFlashAttribute("cannot", "currently unavailable");
            return "redirect:/post_locked";
        }

        postFromDb.setTitle(post.getTitle());
        postFromDb.setContent(post.getContent());

        postFromDb = postService.update(postFromDb);
        postFromDb = postService.unlock(postFromDb);

        return "redirect:/post/" + postFromDb.getId();
    }
}
