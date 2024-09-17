package com.example.item_repo_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.item_repo_spring.models.SubType;
import java.util.List;


@Repository
public interface SubTypeRepository extends JpaRepository<SubType, Integer> {

    // return a list of subtypes and their corresponding total item quantities.
    // AS operator does not work in JPA
    @Query("SELECT s.id, s.name, SUM(i.quantity) FROM SubType s " +
        "JOIN s.items i " +
        "GROUP BY s.id, s.name " +
        "HAVING SUM(i.quantity) < :maxQuantity " +
        "ORDER BY SUM(i.quantity) ASC")
    List<Object[]> findSubTypesWithLimitedItemQuantities(@Param("maxQuantity") int maxQuantity);


    @Query("SELECT s.id, s.name, s.type, SUM(i.quantity) AS sumItemsQuantity FROM SubType s " +
    "JOIN s.items i " +
    "GROUP BY s.id, s.name, s.type ")
    List<Object[]> findSubTypesWithItemQuantities();
}
