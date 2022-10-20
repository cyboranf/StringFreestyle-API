package com.example.StringFreestyleApi.web;

import com.example.StringFreestyleApi.domain.Sign;
import com.example.StringFreestyleApi.service.SignService;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/generate")
public class StringCreatorController {
    private long id = 1;

    private final SignService signService;

    public StringCreatorController(SignService signService) {

        this.signService = signService;
    }

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
            generatedWordsList.add("Error: u cant build: " + wanted + " strings, because " + maxQuantity + " is max");
        }
        return generatedWordsList;
    }

    private List<String> generatedWordsList = new ArrayList<>();
    private long maxQuantity = 0;


    void generateStrings(String str, char[] data,
                         long last, int index, long rowLimit) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            data[index] = str.charAt(i);
            if (index == last) {
                if (rowLimit == maxQuantity) {
                    return;
                }
                generatedWordsList.add(new String(data));
                maxQuantity++;
            } else
                generateStrings(str, data, last,
                        index + 1, rowLimit);
        }

    }

    void createDataForGenerated(String str, long length, long rowLimit) {
        if (rowLimit == maxQuantity) {
            return;
        }
        char[] data = new char[(int) length];
        char[] temp = str.toCharArray();

        Arrays.sort(temp);
        str = new String(temp);
        generateStrings(str, data, length - 1, 0, rowLimit);
    }

    List<String> changeGeneratedToList(String str, long min, long max, long rowLimit) {
        while (min != max + 1) {
            createDataForGenerated(str, min, rowLimit);
            min++;
        }
        List<String> strList = generatedWordsList;
        return strList;
    }

    long maxPossibilities(String str, long min, long max) {
        double mip = 0;
        for (int i = (int) min; i <= max; i++) {
            mip = mip + Math.pow(str.length(), i);
        }
        return (long) mip;
    }

    private void createAndWriteToFile(List<String> stringList, boolean possible, long min, long max, String elements, long wantedQuantity) throws IOException {
        String nameFile = "generate" + min + max + elements + wantedQuantity + ".txt";
        if (possible) {
            File file = new File(nameFile);
            if (!file.exists()) {
                file.createNewFile();

            }

            PrintWriter printWriter = new PrintWriter(file);
            for (String permutation : stringList) {
                printWriter.println(permutation);
            }
            printWriter.close();
        }

    }

}
