package com.example.catproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.Map;

import java.io.IOException;


public class mainController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    protected void loginButtonClick(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        // Load user data from the CSV file
        Map<String, String> userData = CSVHandler.loadUserData();

        // Create an alert to show messages
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Login Attempt");

        // Validate username and password
        if (username.isEmpty() || password.isEmpty()) {
            alert.setHeaderText("Error");
            alert.setContentText("Please enter both username and password.");
        } else if (userData.containsKey(username) && userData.get(username).equals(password)) {
            alert.setHeaderText("Success");
            alert.setContentText("Login successful for user: " + username);
            alert.showAndWait(); // Wait for user interaction before continuing

            goToMainMenu(event);
        } else {
            alert.setHeaderText("Error");
            alert.setContentText("Invalid username or password.");
        }

        alert.showAndWait(); // Show the alert
    }


    private void goToMainMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
            AnchorPane mainMenuLayout = loader.load();

            Scene mainMenuScene = new Scene(mainMenuLayout);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(mainMenuScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to Load Main Menu");
            alert.setContentText("There was an error while loading the main menu.");
            alert.showAndWait();
        }
    }

    public void goToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
            AnchorPane mainMenuLayout = loader.load();

            Scene mainMenuScene = new Scene(mainMenuLayout);

            Stage stage = (Stage) txtUsername.getScene().getWindow();

            stage.setScene(mainMenuScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to Load Register Page");
            alert.setContentText("There was an error while loading the register page.");
            alert.showAndWait();
        }
    }
}