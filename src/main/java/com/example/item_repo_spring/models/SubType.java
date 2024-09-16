package com.example.item_repo_spring.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import java.util.*;


@Entity
@Table(name="sub_type")
public class SubType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name", unique=true)
    private String name;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="type_id", nullable = false)
    private Type type;

    @OneToMany(mappedBy = "subType", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Item> items;


    public SubType() {
    }

    public SubType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SubType [id=" + id + ", name=" + name + ", type=" + type + "]";
    }

    
    
}
