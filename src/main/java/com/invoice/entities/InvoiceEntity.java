package com.invoice.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "invoice")
public class InvoiceEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "customer_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private CustomerEntity customer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice")
    private List<LineItemEntity> lineItems;

    @Column(name = "creation_time")
    private Date creationTime;

    @Column(name = "total_amount")
    private double totalAmount;

    public InvoiceEntity() {
    }

    public InvoiceEntity(CustomerEntity customer, List<LineItemEntity> lineItems, Date creationTime, double totalAmount) {
        this.customer = customer;
        this.lineItems = lineItems;
        this.creationTime = creationTime;
        this.totalAmount = totalAmount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public List<LineItemEntity> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItemEntity> lineItems) {
        this.lineItems = lineItems;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
