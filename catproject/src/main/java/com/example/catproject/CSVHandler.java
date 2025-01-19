package com.example.catproject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CSVHandler {
    private static final String CSV_FILE_PATH = "src/main/resources/users.csv";

    public static Map<String, String> loadUserData() {
        Map<String, String> userMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // Split the CSV line by comma
                String username = data[1];
                String password = data[2];
                userMap.put(username, password); // Add username-password pair to the map
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userMap;
    }
}
