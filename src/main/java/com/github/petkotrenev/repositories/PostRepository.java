package com.github.petkotrenev.repositories;

import com.github.petkotrenev.entities.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

    List<Post> findTop5OrderByCreationDate(Pageable pageRequest);

}
