package com.example.catproject;

public class CartItem {

    private final String itemName;
    private final int quantity;
    private final double price;
    private final double subtotal;

    public CartItem(String itemName, int quantity, double price, double subtotal) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getSubtotal() {
        return subtotal;
    }
}
