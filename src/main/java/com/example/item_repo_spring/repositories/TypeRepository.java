package com.example.item_repo_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.item_repo_spring.models.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer>{
    
}


