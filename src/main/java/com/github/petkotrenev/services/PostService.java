package com.github.petkotrenev.services;

import com.github.petkotrenev.entities.Post;
import com.github.petkotrenev.entities.User;

import java.util.List;

public interface PostService {

    List<Post> findAll();

    List<Post> findLatest5();

    Post findById(Long id);

    Post create(Post post);

    Post create(Post post, User user);

    Post update(Post post);

    void deleteById(Long id);


    Post lockForUser(Post post, User user);

    Post unlock(Post post);

    boolean isPostLockedForUser(Post post, User currentUser);
}
