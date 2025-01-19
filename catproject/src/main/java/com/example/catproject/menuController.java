package com.example.catproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class menuController {

    public Button cartButton;
    @FXML
    private TextField r1c1qty;
    @FXML
    private TextField r2c1qty;
    @FXML
    private TextField r1c2qty;
    @FXML
    private TextField r2c2qty;
    @FXML
    private Button r1c1add;
    @FXML
    private Button r2c1add;
    @FXML
    private Button r1c2add;
    @FXML
    private Button r2c2add;

    // Method to handle adding to cart
    @FXML
    public void addToCart(ActionEvent event) {
        Object source = event.getSource();
        String productName = "";
        double price = 0.0;
        String quantityText = "";

        if (source == r1c1add) {
            productName = "RTX 4070TI 12GB";
            price = 3399.99;
            quantityText = r1c1qty.getText();
        } else if (source == r2c1add) {
            productName = "RX7800XT 16GB";
            price = 3199.99;
            quantityText = r2c1qty.getText();
        } else if (source == r1c2add) {
            productName = "RTX 3050 6GB (Gigabyte)";
            price = 1299.99;
            quantityText = r1c2qty.getText();
        } else if (source == r2c2add) {
            productName = "RTX 3050 6GB";
            price = 1399.99;
            quantityText = r2c2qty.getText();
        }

        try {
            int quantity = Integer.parseInt(quantityText);

            // Save to CSV file
            saveToCart(productName, price, quantity);

            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "Success", "Item added to cart successfully!");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid quantity.");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not update the cart file.");
            e.printStackTrace();
        }
    }

    // Method to save data to cart CSV
    private void saveToCart(String productName, double price, int quantity) throws IOException {
        String filePath = "src/main/resources/cart.csv";
        List<String> lines = new ArrayList<>();
        boolean productFound = false;

        // Read the existing cart data
        File file = new File(filePath);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3 && parts[0].equals(productName)) {
                        // Update the quantity of the existing product
                        int existingQuantity = Integer.parseInt(parts[2]);
                        int newQuantity = existingQuantity + quantity;
                        lines.add(productName + "," + price + "," + newQuantity);
                        productFound = true;
                    } else {
                        lines.add(line); // Retain other products
                    }
                }
            }
        }

        // If the product was not found, add it as a new line
        if (!productFound) {
            lines.add(productName + "," + price + "," + quantity);
        }

        // Write back the updated cart data
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        }
    }

    // Utility method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void navigateToCart(ActionEvent event) {
        navigateToScene(event, "cart.fxml");
    }

    @FXML
    private void navigateToCPU(ActionEvent event) {
        navigateToScene(event, "cpuMenu.fxml");
    }

    // Method to navigate to RAM section
    @FXML
    private void navigateToRAM(ActionEvent event) {
        navigateToScene(event, "ramMenu.fxml");
    }

    private void navigateToScene(ActionEvent event, String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load the requested scene.");
            e.printStackTrace();
        }
    }
}
