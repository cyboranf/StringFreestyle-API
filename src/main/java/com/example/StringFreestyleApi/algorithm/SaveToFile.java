package com.example.StringFreestyleApi.algorithm;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SaveToFile {
    //create file txt with created Strings
    //Files name = minSize-maxSize-elements of String-how much user want Strings to generate
    public static void createAndWriteToFile(List<String> stringList, boolean possible, long min, long max, String elements, long wantedQuantity) throws IOException {
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
