package com.invoice.rest.models;

import javax.validation.constraints.NotNull;

public class LineItem {
    private long id;
    @NotNull(message = "Product cannot be null")
    private Product product;
    @NotNull(message = "Product quantity cannot be null")
    private int quantity;
    private double amount;

    public LineItem() {}
    public LineItem(long id, Product product, int quantity, double amount) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.amount = amount;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LineItem{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", amount=" + amount +
                '}';
    }
}
