package com.example.item_repo_spring.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.item_repo_spring.models.Type;
import com.example.item_repo_spring.models.SubType;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer>{
    List<SubType> findByTypeId(Integer typeId);
}


