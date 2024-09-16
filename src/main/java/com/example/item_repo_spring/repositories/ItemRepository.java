package com.example.item_repo_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.item_repo_spring.models.Item;

import java.util.*;
import java.time.LocalDate;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{

    // find whether an item exist by name, expiryDate and subTypeId
    Optional<Item> findByNameAndExpiryDateAndSubType_Id(String name, LocalDate expiryDate, Integer subTypeId);


    // find items with epxirDate before the specified date, and sort in ascending order
    List<Item> findByExpiryDateBeforeOrderByExpiryDateAsc(LocalDate date);
    
    // get all items order by subtype name
    @Query("SELECT i FROM Item i ORDER BY i.subType.name ASC") 
    List<Item> findAllItemsOrderedBySubType();
    
}
