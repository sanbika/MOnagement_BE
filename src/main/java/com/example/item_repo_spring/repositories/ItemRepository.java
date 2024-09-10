package com.example.item_repo_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.item_repo_spring.models.Item;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;
import java.time.LocalDate;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{
    
    //i.id <> :id: Ensures that the record being checked is not the current record that is being updated. <> means "not equal to".
    @Query("SELECT COUNT(i) > 0 FROM Item i WHERE i.name = :name AND i.expirDate = :expirDate AND i.type.id = :typeId AND i.id <> :id")
    boolean existsByNameAndExpirDateAndTypeId(
        @Param("name") String name, 
        @Param("expirDate") LocalDate expirDate, 
        @Param("typeId") Integer typeId, 
        @Param("id") Long id);


    // Get items that quantity is less than the specific quantity
    @Query("SELECT i FROM Item i WHERE i.quantity <= :quantity")
    List<Item> findItemsWithQuantityLessThan(@Param("quantity") int quantity);

    // Get items that will be expired before the specific date
    @Query("SELECT i FROM Item i WHERE i.expirDate < :date")
    List<Item> findItemsWithExpirDateBefore(@Param("date") LocalDate date);

}
