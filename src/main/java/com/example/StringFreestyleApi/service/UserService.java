package com.example.StringFreestyleApi.service;

import com.example.StringFreestyleApi.domain.User;
import com.example.StringFreestyleApi.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user){
        userRepository.save(user);
        return user;
    }
    public User findById(Long id){
        return userRepository.findAllById(id).orElseThrow();
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }
}
