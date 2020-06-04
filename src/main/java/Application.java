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
        int k = 4;
        String fileName = "/Users/oliviachisman/dev/depaul/ai1/k-nearest-neighbor/src/main/resources/knn-csc480-a4.csv";
        DataParser parser = new DataParser(new File(fileName));
        RatingPredictor predictor = new RatingPredictor(parser.getTrainingUsers());
        for (String user : parser.getTestUsers().keySet()) {
            for (String title : parser.getMovieNames()) {
                double predictedRating = predictor.predictRating(parser.getTestUsers().get(user), title, k);
                double actualRating;
                if (parser.getTestUsers().get(user).containsKey(title)) {
                    actualRating = parser.getTestUsers().get(user).get(title);
                    System.out.println("Predicted rating: " + predictedRating + ", actual rating: " + actualRating);
                }
                System.out.println("Predicted rating: " + predictedRating + ", actual rating: Not Rated");
            }
        }
        System.out.println("Done");
    }
}
