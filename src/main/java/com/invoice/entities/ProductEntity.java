package com.invoice.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "product")
public class ProductEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private double price;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
    private List<LineItemEntity> lineItem;

    public ProductEntity() {
    }

    public ProductEntity(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<LineItemEntity> getLineItem() {
        return lineItem;
    }

    public void setLineItem(List<LineItemEntity> lineItem) {
        this.lineItem = lineItem;
    }
}
