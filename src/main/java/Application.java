package main.java;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by oliviachisman on 6/2/20
 */
public class Application {

    public static void main(String[] args) throws IOException {
        String fileName = "/Users/oliviachisman/dev/depaul/ai1/k-nearest-neighbor/src/main/resources/knn-csc480-a4.csv";
        DataParser parser = new DataParser(new File(fileName));

        System.out.println("Done");
    }
}
