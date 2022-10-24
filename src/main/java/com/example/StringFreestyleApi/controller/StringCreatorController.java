package com.example.StringFreestyleApi.controller;

import com.example.StringFreestyleApi.algorithm.Finder;
import com.example.StringFreestyleApi.algorithm.Permutations;
import com.example.StringFreestyleApi.domain.Sign;
import com.example.StringFreestyleApi.service.SignService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.StringFreestyleApi.algorithm.Permutations.*;
import static com.example.StringFreestyleApi.algorithm.SaveToFile.createAndWriteToFile;

@RestController
@RequestMapping("/generate")
public class StringCreatorController {
    private long jobs = 0;
    private long id = 1;

    private final SignService signService;

    public StringCreatorController(SignService signService) {

        this.signService = signService;
    }


    //create task to generate
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
        sign.setDone(false);

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
            sign.setDone(true);
        } else {
            generatedWordsList.add("You wanted to build " + wanted + " strings with minimum length = " + min + " and maximum = " + max + " made by chars from: " + elements);
            generatedWordsList.add("Error: u cant build: " + wanted + " strings, because " + Permutations.maxQuantity + " is max");
        }
        return generatedWordsList;
    }

    @GetMapping("/")
    public List<String> wordsGeneratorFromQueue() throws IOException {
        generatedWordsList.clear();
        Finder finder = new Finder(signService);
        List<Long> listFromFinder = finder.wordsToGenerate();
        jobs = listFromFinder.size();
        int i = 0;
        if (listFromFinder.isEmpty()) {
            generatedWordsList.add("Error: Havent Parameters to create String, use method Post and add task.");
        } else {
            while (jobs >= 1) {
                i++;


                Sign stringToGenerate = signService.findByWant(listFromFinder.get(i - 1));
                if (!stringToGenerate.isDone() && stringToGenerate.isPossible()) {
                    generatedWordsList.add("You wanted to build " + stringToGenerate.getWantQuantity() + " strings with minimum length = " + stringToGenerate.getMinSizeString() + " and maximum = " + stringToGenerate.getMaxSizeString() + " made by chars from: " + stringToGenerate.getElementsOfString());
                    generatedWordsList.add("Jobs: " + jobs);
                    changeGeneratedToList(stringToGenerate.getElementsOfString(), stringToGenerate.getMinSizeString(), stringToGenerate.getMaxSizeString(), stringToGenerate.getWantQuantity());
                    createAndWriteToFile(generatedWordsList, stringToGenerate.isPossible(), stringToGenerate.getMinSizeString(), stringToGenerate.getMaxSizeString(), stringToGenerate.getElementsOfString(), stringToGenerate.getWantQuantity());
                    stringToGenerate.setDone(true);
                    jobs--;

                }
            }
        }


        return generatedWordsList;
    }


}
