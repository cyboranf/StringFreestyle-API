package com.example.StringFreestyleApi.repository;

import com.example.StringFreestyleApi.domain.Sign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SignRepository extends JpaRepository<Sign, Long> {
    Sign save(Sign sign);
    Optional<Sign>findAllById(Long id);
    void deleteById(Long id);
    List<Sign> findAll();
}
