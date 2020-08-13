package com.invoice.rest.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Invoice {
    private long id;
    private Date creationTime;
    @NotNull(message = "Customer cannot be null")
    private Customer customer;
    @NotNull(message = "LineItems cannot be null")
    private List<LineItem> lineItems;
    private double totalAmount;

    public Invoice () {}

    public Invoice(long id, Date creationTime, Customer customer, List<LineItem> lineItems, double totalAmount) {
        this.id = id;
        this.creationTime = creationTime;
        this.customer = customer;
        this.lineItems = lineItems;
        this.totalAmount = totalAmount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", creationTime=" + creationTime +
                ", customer=" + customer +
                ", lineItems=" + lineItems +
                '}';
    }
}
