package com.example.item_repo_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.item_repo_spring.models.Item;

import java.util.*;
import java.time.LocalDate;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{

    // find whether an item exist by name, expirDate and subTypeId
    Optional<Item> findByNameAndExpirDateAndSubType_Id(String name, LocalDate expirDate, Long subTypeId);


    // find items with epxirDate before the specified date, and sort in ascending order
    List<Item> findByExpirDateBeforeOrderByExpirDateAsc(LocalDate date);



}
