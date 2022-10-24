package com.example.StringFreestyleApi.service;

import com.example.StringFreestyleApi.domain.Sign;
import com.example.StringFreestyleApi.repository.SignRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SignService {
    private final SignRepository signRepository;

    public SignService(SignRepository signRepository) {
        this.signRepository = signRepository;
    }

    public Sign saveSign(Sign sign) {
        signRepository.save(sign);
        return sign;
    }

    public Sign findById(Long id) {
        return signRepository.findAllById(id).orElseThrow();
    }

    public List<Sign> findAll() {
        return signRepository.findAll();
    }

    public Sign findByWant(long wanted) {
        return signRepository.findSignByWantQuantity(wanted);
    }

}
