package com.example.StringFreestyleApi.algorithm;

import com.example.StringFreestyleApi.domain.Sign;
import com.example.StringFreestyleApi.service.SignService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Finder {
    private final SignService signService;

    public Finder(SignService signService) {
        this.signService = signService;
    }

    public List<Long> wordsToGenerate() {
        List<Sign> allSigns = signService.findAll();
        List<Long> notGeneratedYet = new ArrayList<>();
        for (Sign sign : allSigns) {
            if (!sign.isDone() && sign.isPossible()) {
                notGeneratedYet.add(sign.getWantQuantity());
            }
        }
        Collections.sort(notGeneratedYet);
        return notGeneratedYet;

    }
}
