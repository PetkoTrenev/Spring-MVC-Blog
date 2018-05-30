package com.github.petkotrenev.services;

import com.github.petkotrenev.entities.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    public User findByUsername(String username);

    void create(User user);

    void edit(User user);

    void deleteById(Long id);

}
