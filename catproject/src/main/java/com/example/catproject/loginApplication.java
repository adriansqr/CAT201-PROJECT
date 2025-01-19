package com.example.catproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class loginApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(loginApplication.class.getResource("login.fxml"));

        // Create the scene
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        // Load and apply the CSS file
        String cssPath = getClass().getResource("/styles.css").toExternalForm();
        scene.getStylesheets().add(cssPath);

        // Set up the stage
        stage.setTitle("Computer Builder Website");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}