package com.github.petkotrenev.services;

import com.github.petkotrenev.entities.Post;
import com.github.petkotrenev.entities.User;
import com.github.petkotrenev.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceJpaImpl implements PostService{

    @Autowired
    PostRepository postRepository;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> findLatest5() {
        return postRepository.findTop5OrderByCreationDate(new PageRequest(0,5));
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findOne(id);
    }

    @Override
    public void create(Post post) { postRepository.save(post);
    }

    @Override
    public void create(Post post, User user) {
        post.setCreator(user);
        create(post);
    }

    @Override
    public void edit(Post post) {
        postRepository.save(post);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.delete(id);
    }
}
