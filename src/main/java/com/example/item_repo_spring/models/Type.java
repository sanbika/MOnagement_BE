package com.example.item_repo_spring.models;

import java.util.*;
import jakarta.persistence.*;


@Entity
@Table(name="type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name", unique=true)
    private String name;

    // @OneToMany(cascade = CascadeType.ALL)
    // @JoinColumn(name = "")
    // private Set<Item> items;

    public Type (){

    }

    public Type (Integer id){
        this.id = id;
    }

    public Type(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    // public Set<Item> getItems() {
    //     return items;
    // }

    // public void setItems(Set<Item> items) {
    //     this.items = items;
    // }

    
    @Override
    public String toString(){
        return "Type{" + "id" + id + "name" + name;
    }
    
}
