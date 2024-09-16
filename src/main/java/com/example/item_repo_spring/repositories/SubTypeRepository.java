package com.example.item_repo_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

import com.example.item_repo_spring.models.SubType;
import com.example.item_repo_spring.models.Item;
import java.util.List;


@Repository
public interface SubTypeRepository extends JpaRepository<SubType, Integer> {
    
    // get items with a specified subtype id
    List<Item> findBySubTypeId(Integer subTypeId);

    // return a list of subtypes and their corresponding total item quantities.
    @Query("SELECT s, SUM(i.quantity) FROM SubType s " +
        "JOIN s.items i " +
        "GROUP BY s " +
        "HAVING SUM(i.quantity) < :maxQuantity " +
        "ORDER BY SUM(i.quantity) ASC")
    List<Object[]> findSubTypesWithLimitedItemQuantities(@Param("maxQuantity") int maxQuantity);
}
