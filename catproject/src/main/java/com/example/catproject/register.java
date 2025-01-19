package com.example.catproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class register {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtPassword1;

    private static final String CSV_FILE_PATH = "src/main/resources/users.csv";

    @FXML
    protected void loginButtonClick() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtPassword1.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields are required!");
        } else if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Passwords do not match!");
        } else if (isUsernameTaken(username)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Username is already taken!");
        } else {
            // Save the user to the CSV file
            boolean success = saveUserToCSV(username, password);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User has been registered successfully.");
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration Failed", "Failed to register the user.");
            }
        }
    }

    private boolean saveUserToCSV(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, true))) {
            int nextId = getNextId();
            writer.write(nextId + "," + username + "," + password);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int getNextId() {
        int lastId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty() && !line.startsWith("id,")) { // Skip the header
                    String[] data = line.split(",");
                    lastId = Integer.parseInt(data[0]); // Get the id column
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return lastId + 1;
    }

    private boolean isUsernameTaken(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty() && !line.startsWith("id,")) { // Skip the header
                    String[] data = line.split(",");
                    if (data[1].equals(username)) {
                        return true; // Username is already taken
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        txtUsername.clear();
        txtPassword.clear();
        txtPassword1.clear();
    }

    public void backButtonClick(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            AnchorPane loginLayout = loader.load();

            Scene loginScene = new Scene(loginLayout);

            // Get the current stage from the event source
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(loginScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to Load Login Page");
            alert.setContentText("There was an error while loading the login page.");
            alert.showAndWait();
        }
    }
}
