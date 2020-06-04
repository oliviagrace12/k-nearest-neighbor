package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by oliviachisman on 6/2/20
 */
public class DataParser {

    public HashMap<String, HashMap<String, Integer>> trainingUsers;
    public HashMap<String, HashMap<String, Integer>> testUsers;
    public List<String> movieNames;

    public DataParser(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        movieNames = getMovieNames(line);
        trainingUsers = new HashMap<>();
        testUsers = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length > 0 && parts[0].charAt(0) == 'U') {
                createUser(line, trainingUsers, movieNames);
            } else if (parts.length > 0 && parts[0].charAt(0) == 'N') {
                createUser(line, testUsers, movieNames);
            }
        }
    }

    public HashMap<String, HashMap<String, Integer>> getTrainingUsers() {
        return trainingUsers;
    }

    public HashMap<String, HashMap<String, Integer>> getTestUsers() {
        return testUsers;
    }

    public List<String> getMovieNames() {
        return movieNames;
    }

    private void createUser(String line, HashMap<String, HashMap<String, Integer>> testUsers, List<String> movieNames) {
        String[] parts = line.split(",");
        String name = parts[0];
        HashMap<String, Integer> ratings = new HashMap<>();

        for (int i = 1; i < parts.length; i++) {
            int rating;
            if (!parts[i].equals(" ") && !parts[i].equals("") && (rating  = Integer.parseInt(parts[i])) != 0) {
                ratings.put(movieNames.get(i - 1), rating);
            }
        }

        testUsers.put(name, ratings);
    }

    private List<String> getMovieNames(String line) {
        List<String> movieNames = new ArrayList<>();
        String[] parts = line.split(",");
        for (int i = 1; i < parts.length; i++) {
            movieNames.add(parts[i]);
        }
        return movieNames;
    }
}
