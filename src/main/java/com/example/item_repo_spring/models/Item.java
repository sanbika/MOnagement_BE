package com.example.item_repo_spring.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name="Item", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "expiryDate", "subType"})})
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private LocalDate expiryDate;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name="sub_type_id", nullable = false)
    @JsonBackReference
    private SubType subType;


    public Item(){

    }
    
    public Item(String name, LocalDate expiryDate, Integer quantity, SubType subType) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
        this.subType = subType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public SubType getSubType() {
        return subType;
    }

    public void setSubType(SubType subType) {
        this.subType = subType;
    }

    @Override
    public String toString() {
        return "Item [id=" + id + ", name=" + name + ", expiryDate=" + expiryDate + ", quantity=" + quantity
                + ", subType=" + subType + "]";
    }
    
}
