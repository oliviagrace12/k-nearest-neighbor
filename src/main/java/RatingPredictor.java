package main.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by oliviachisman on 6/2/20
 */
public class RatingPredictor {

    private final HashMap<String, HashMap<String, Integer>> trainingUsers;

    public RatingPredictor(HashMap<String, HashMap<String, Integer>> trainingUsers) {
        this.trainingUsers = trainingUsers;
    }

    public double predictRating(HashMap<String, Integer> user, String title, int k) {
        List<TrainingUser> trainingUserCorrelations = new ArrayList<>();
        for (String trainUser : trainingUsers.keySet()) {
            double correlation = calculateCorrelation(user, trainingUsers.get(trainUser));
            trainingUserCorrelations.add(new TrainingUser(trainUser, correlation));
        }

        trainingUserCorrelations.stream().sorted((u1, u2) -> u1.correlation >= u2.correlation ? 1 : -1);

        List<TrainingUser> nearestNeighbors = trainingUserCorrelations.subList(0,k);
        double predictedRatingSum = 0;
        for (TrainingUser t : nearestNeighbors) {
            if (trainingUsers.get(t.userName).containsKey(title)) {
                predictedRatingSum += t.correlation * trainingUsers.get(t.userName).get(title);
            }
        }

        return predictedRatingSum / k;
    }

    private class TrainingUser {
        String userName;
        double correlation;

        public TrainingUser(String userName, double correlation) {
            this.userName = userName;
            this.correlation = correlation;
        }
    }

    private Double calculateCorrelation(HashMap<String, Integer> userX, HashMap<String, Integer> userY) {
        List<Integer> commonX = new ArrayList<>();
        List<Integer> commonY = new ArrayList<>();

        for (String title : userX.keySet()) {
            if (userY.containsKey(title)) {
                commonX.add(userX.get(title));
                commonY.add(userY.get(title));
            }
        }

        if (commonX.size() < 1) {
            return 0.0;
        }

        int n = commonX.size();
        int xSum = 0;
        int ySum = 0;
        int xySum = 0;
        int xSquaredSum = 0;
        int ySquaredSum = 0;
        for (int i = 0; i < n; i++) {
            int x = commonX.get(i);
            int y = commonY.get(i);
            xSum += x;
            ySum += y;
            xySum += x * y;
            xSquaredSum += x * x;
            ySquaredSum += y * y;
        }

        double numerator = (n * xySum) - (xSum * ySum);
        double denominator  = ((n * xSquaredSum) - (xSum * ySum)) * ((n * ySquaredSum) - (ySum * ySum));

        return numerator / Math.sqrt(denominator);
    }
}
