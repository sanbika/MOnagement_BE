package com.example.item_repo_spring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.item_repo_spring.models.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer>{
    @Query("SELECT i FROM Type i WHERE i.name=?1")
    Optional<Type> findTypeByName(String name);
}


