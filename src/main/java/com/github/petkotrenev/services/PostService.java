package com.github.petkotrenev.services;

import com.github.petkotrenev.entities.Post;
import com.github.petkotrenev.entities.User;

import java.util.List;

public interface PostService {

    List<Post> findAll();

    List<Post> findLatest5();

    Post findById(Long id);

    void create(Post post);

    void create(Post post, User user);

    void edit(Post post);

    void deleteById(Long id);


}
