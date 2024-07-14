package com.test.Repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.Entity.User;
@Repository
public interface UserRepositry extends JpaRepository<User, Long>{


	   Optional<User> findByname(String username);

}
