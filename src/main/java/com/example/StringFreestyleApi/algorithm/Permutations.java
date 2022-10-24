package com.example.StringFreestyleApi.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutations {
    public static List<String> generatedWordsList = new ArrayList<>();
    public static long maxQuantity = 0;


    static void generateStrings(String str, char[] data,
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

    public static void createDataForGenerated(String str, long length, long rowLimit) {
        if (rowLimit == maxQuantity) {
            return;
        }
        char[] data = new char[(int) length];
        char[] temp = str.toCharArray();

        Arrays.sort(temp);
        str = new String(temp);
        generateStrings(str, data, length - 1, 0, rowLimit);
    }

    public static List<String> changeGeneratedToList(String str, long min, long max, long rowLimit) {
        while (min != max + 1) {
            createDataForGenerated(str, min, rowLimit);
            min++;
        }
        List<String> strList = generatedWordsList;
        return strList;
    }

    public static long maxPossibilities(String str, long min, long max) {
        double mip = 0;
        for (int i = (int) min; i <= max; i++) {
            mip = mip + Math.pow(str.length(), i);
        }
        return (long) mip;
    }
}
