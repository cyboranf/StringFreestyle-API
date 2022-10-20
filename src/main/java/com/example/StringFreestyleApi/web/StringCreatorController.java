package com.example.StringFreestyleApi.web;

import com.example.StringFreestyleApi.domain.Sign;
import com.example.StringFreestyleApi.domain.User;
import com.example.StringFreestyleApi.service.SignService;
import com.example.StringFreestyleApi.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/generate")
public class StringCreatorController {
    private long id = 1;
    private final UserService userService;
    private final SignService signService;

    public StringCreatorController(UserService userService, SignService signService) {
        this.userService = userService;
        this.signService = signService;
    }

    @PostMapping("/{min}/{max}/{elements}/{wanted}")
    public String defineGenerate(@PathVariable long min, @PathVariable long max, @PathVariable String elements, @PathVariable long wanted) {
        User user = new User();
        Sign sign = new Sign();
        sign.setId(id);
        sign.setMinSizeString(min);
        sign.setMaxSizeString(max);
        sign.setElementsOfString(elements);
        sign.setWantQuantity(wanted);


        sign.setMaxQuantity(maxPossibilities(elements, min, max));
        maxQuantityTest = 0;
        signService.saveSign(sign);
        id++;
        return sign.toString();
    }

    @GetMapping("/{id}")
    public List<String> generateStrings(@PathVariable long id) {
        generatedWordsList.clear();

        Sign signParams = signService.findById(id);
        long min = signParams.getMinSizeString();
        long max = signParams.getMaxSizeString();
        String elements = signParams.getElementsOfString();
        long wantedQuantity = signParams.getWantQuantity();
        long maxQuantity = signParams.getMaxQuantity();

        long minAlso = min;

        if (maxQuantity >= wantedQuantity) {
            generatedWordsList.add("You wanted to build "+wantedQuantity+" strings with minimum length = "+min+" and maximum = "+max+" made by chars from: "+elements);
            changeGeneratedToList(elements, min, max, wantedQuantity);
        } else {
            generatedWordsList.add("You wanted to build "+wantedQuantity+" strings with minimum length = "+min+" and maximum = "+max+" made by chars from: "+elements);
            generatedWordsList.add("Error: u cant build: " + wantedQuantity + " strings, because " + maxQuantity + " is max");
        }
        return generatedWordsList;

    }

    private List<String> generatedWordsList = new ArrayList<>();
    private long maxQuantity = 0;
    private long maxQuantityTest = 0;

    void generateStrings(String str, char[] data,
                         long last, int index, long rowLimit) {
        int length = str.length();


        for (int i = 0; i < length; i++) {

            data[index] = str.charAt(i);

            if (index == last) {
                if (rowLimit == maxQuantity) {
                    return;
                }
                ;
                //System.out.println(new String(data));
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
        ;
        char[] data = new char[(int) length];
        char[] temp = str.toCharArray();

        Arrays.sort(temp);
        str = new String(temp);
        List<String> strList = new ArrayList<>();

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

}
