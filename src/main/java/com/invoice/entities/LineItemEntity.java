package com.invoice.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "line_items")
public class LineItemEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "invoice_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private InvoiceEntity invoice;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private ProductEntity product;

    private int quantity;

    private double amount;

    public LineItemEntity() {
    }

    public LineItemEntity(ProductEntity product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public InvoiceEntity getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceEntity invoice) {
        this.invoice = invoice;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
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
}
