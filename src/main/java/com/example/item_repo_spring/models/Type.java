package com.example.item_repo_spring.models;

import jakarta.persistence.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name="type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name", unique=true)
    private String name;

    @OneToMany(mappedBy = "type")
    @JsonManagedReference
    private List<SubType> subTypes;

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

    public List<SubType> getSubTypes() {
        return subTypes;
    }
    
    @Override
    public String toString(){
        return "Type{" + "id" + id + "name" + name;
    }
    
}
