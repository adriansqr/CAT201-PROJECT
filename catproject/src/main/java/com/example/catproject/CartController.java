package com.example.catproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CartController {

    @FXML
    private TableView<CartItem> cartTable;

    @FXML
    private TableColumn<CartItem, String> itemNameColumn;

    @FXML
    private TableColumn<CartItem, Integer> quantityColumn;

    @FXML
    private TableColumn<CartItem, Double> priceColumn;

    @FXML
    private TableColumn<CartItem, Double> subtotalColumn;

    @FXML
    private Label totalPriceLabel;

    private final String CART_FILE_PATH = "src/main/resources/cart.csv"; // Update this path to the actual location

    @FXML
    public void initialize() {
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        subtotalColumn.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        loadCartData();
    }

    private void loadCartData() {
        ObservableList<CartItem> cartItems = FXCollections.observableArrayList();
        double total = 0.0;

        try (BufferedReader br = new BufferedReader(new FileReader(CART_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String itemName = data[0];
                    double price = Double.parseDouble(data[1]);
                    int quantity = Integer.parseInt(data[2]);
                    double subtotal = price * quantity;

                    cartItems.add(new CartItem(itemName, quantity, price, subtotal));
                    total += subtotal;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally, you can add logging or show an error dialog to the user.
        }

        cartTable.setItems(cartItems);
        totalPriceLabel.setText(String.format("Total: RM %.2f", total));
    }

    @FXML
    public void navigateBack(ActionEvent event) {
        navigateToScene(event, "mainMenu.fxml");    }

    @FXML
    public void checkout() {
        // Implement checkout logic.
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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
