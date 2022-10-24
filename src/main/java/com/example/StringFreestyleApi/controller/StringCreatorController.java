package com.example.StringFreestyleApi.controller;

import com.example.StringFreestyleApi.algorithm.Permutations;
import com.example.StringFreestyleApi.domain.Sign;
import com.example.StringFreestyleApi.service.SignService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;

import static com.example.StringFreestyleApi.algorithm.Permutations.*;
import static com.example.StringFreestyleApi.algorithm.SaveToFile.createAndWriteToFile;

@RestController
@RequestMapping("/generate")
public class StringCreatorController {
//    private long jobs = 0;
    private long id = 1;

    private final SignService signService;

    public StringCreatorController(SignService signService) {

        this.signService = signService;
    }


    //Create task to generate
    @Async
    @PostMapping("/{min}/{max}/{elements}/{wanted}")
    public String defineWhatGenerate(@PathVariable long min, @PathVariable long max, @PathVariable String elements, @PathVariable long wanted) {

        Sign sign = new Sign();

        List<Sign> signList = signService.findAll();
        for (Sign sign1 : signList) {
            id++;
        }

        sign.setId(id);
        sign.setMinSizeString(min);
        sign.setMaxSizeString(max);
        sign.setElementsOfString(elements);
        sign.setWantQuantity(wanted);


        sign.setMaxQuantity(maxPossibilities(elements, min, max));
        if (sign.getMaxQuantity() >= wanted) {
            sign.setPossible(true);
        } else {
            sign.setPossible(false);
        }

        signService.saveSign(sign);

        return sign.toString();
    }


    //id is id from database which row u want to generate
    @GetMapping("/{id}")
    public List<String> generateStrings(@PathVariable long id) throws IOException {
        generatedWordsList.clear();

        Sign signParams = signService.findById(id);
        long min = signParams.getMinSizeString();
        long max = signParams.getMaxSizeString();
        String elements = signParams.getElementsOfString();
        long wantedQuantity = signParams.getWantQuantity();
        long maxQuantity = signParams.getMaxQuantity();
        boolean isPossible = signParams.isPossible();


        if (isPossible) {
            generatedWordsList.add("You wanted to build " + wantedQuantity + " strings with minimum length = " + min + " and maximum = " + max + " made by chars from: " + elements);
            changeGeneratedToList(elements, min, max, wantedQuantity);
            createAndWriteToFile(generatedWordsList, isPossible, min, max, elements, wantedQuantity);
        } else {
            generatedWordsList.add("You wanted to build " + wantedQuantity + " strings with minimum length = " + min + " and maximum = " + max + " made by chars from: " + elements);
            generatedWordsList.add("Error: u cant build: " + wantedQuantity + " strings, because " + maxQuantity + " is max");
        }
        return generatedWordsList;

    }


    //You can generate Strings and save them to file and database
    @GetMapping("/{min}/{max}/{elements}/{wanted}")
    public List<String> wordsGeneratorWAnotherLink(@PathVariable long min, @PathVariable long max, @PathVariable String elements, @PathVariable long wanted) throws IOException {
        Sign sign = new Sign();
        List<Sign> signList = signService.findAll();
        for (Sign sign1 : signList) {
            id++;
        }

        sign.setId(id);
        sign.setMinSizeString(min);
        sign.setMaxSizeString(max);
        sign.setElementsOfString(elements);
        sign.setWantQuantity(wanted);


        sign.setMaxQuantity(maxPossibilities(elements, min, max));
        if (sign.getMaxQuantity() >= wanted) {
            sign.setPossible(true);
        } else {
            sign.setPossible(false);
        }
        signService.saveSign(sign);

        if (sign.isPossible()) {
            generatedWordsList.add("You wanted to build " + wanted + " strings with minimum length = " + min + " and maximum = " + max + " made by chars from: " + elements);
            changeGeneratedToList(elements, min, max, wanted);
            createAndWriteToFile(generatedWordsList, sign.isPossible(), min, max, elements, wanted);
        } else {
            generatedWordsList.add("You wanted to build " + wanted + " strings with minimum length = " + min + " and maximum = " + max + " made by chars from: " + elements);
            generatedWordsList.add("Error: u cant build: " + wanted + " strings, because " + Permutations.maxQuantity + " is max");
        }
        return generatedWordsList;
    }


}
