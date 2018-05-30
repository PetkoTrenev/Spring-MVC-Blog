package com.github.petkotrenev.repositories;

import com.github.petkotrenev.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User repository for CRUD operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);
}
