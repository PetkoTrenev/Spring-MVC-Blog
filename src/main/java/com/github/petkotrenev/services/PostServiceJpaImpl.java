package com.github.petkotrenev.services;

import com.github.petkotrenev.entities.Post;
import com.github.petkotrenev.entities.User;
import com.github.petkotrenev.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static java.time.Instant.now;

@Service
public class PostServiceJpaImpl implements PostService{

    public static final int LOCK_DURATION = 10;
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
    public Post create(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post create(Post post, User user) {
        post.setCreator(user);
        post.setCreationDate(LocalDate.now());
        return create(post);
    }

    @Override
    public Post update(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.delete(id);
    }

    @Override
    public Post lockForUser(Post post, User user) {
        post.setLastLockedBy(user);
        post.setLockTimestamp(now()); // ms after era
        return postRepository.save(post);
    }

    @Override
    public Post unlock(Post post) {
        post.setLastLockedBy(null);
        post.setLockTimestamp(null); // ms after era
        return postRepository.save(post);
    }

    @Override
    public boolean isPostLockedForUser(Post post, User user) {
        User lockedBy = post.getLastLockedBy();

        if (lockedBy == null) return false;
        if (lockedBy.getId().equals(user.getId())) return false;

        Duration duration = Duration.between(post.getLockTimestamp(), now());

        return duration.toMinutes() < LOCK_DURATION;
    }
}
