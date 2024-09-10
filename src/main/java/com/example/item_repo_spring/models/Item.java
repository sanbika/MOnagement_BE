package com.example.item_repo_spring.models;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name="Item", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "expirDate", "type"})})
public class Item {
    @Id
    @SequenceGenerator(name = "item_sequence", sequenceName = "item_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_sequence")
    private Long id;

    @Column(nullable = false)
    private String name;

    private LocalDate expirDate;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name="type_id", nullable = false)
    private Type type;


    public Item(){

    }
    
    public Item(String name, LocalDate localDate, Integer quantity, Type type) {
        this.name = name;
        this.expirDate = localDate;
        this.quantity = quantity;
        this.type = type;
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

    public LocalDate getExpirDate() {
        return expirDate;
    }

    public void setExpirDate(LocalDate expirDate) {
        this.expirDate = expirDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Item{" + "id" + id + "name" + name + "expirDate" + expirDate + "quantity" + quantity + "type" + type;
    }

    

    
}
