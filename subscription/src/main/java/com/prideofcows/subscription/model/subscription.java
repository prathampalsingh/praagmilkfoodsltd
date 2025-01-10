package com.prideofcows.subscription.model;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "subscriptions")
public class subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String frequency;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double price_per_unit;

    @Column(nullable = false)
    private Date start_date;

    @Column(nullable = false)
    private Date end_date;

    public void setEndDate(java.util.Date date) {
        throw new UnsupportedOperationException("Unimplemented method 'setEndDate'");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getprice_per_unit() {
        return price_per_unit;
    }

    public void setprice_per_unit(Double price_per_unit) {
        this.price_per_unit = price_per_unit;
    }

    public Date getStartDate() {
        return start_date;
    }

    public void setStartDate(Date startDate) {
        this.start_date = start_date;
    }

    public Date getEndDate() {
        return end_date;
    }
    
}
