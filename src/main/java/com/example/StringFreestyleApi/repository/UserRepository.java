package com.example.StringFreestyleApi.repository;

import com.example.StringFreestyleApi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);
    Optional<User> findAllById(Long id);
    void deleteById(Long id);
}
