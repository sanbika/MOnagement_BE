package com.example.item_repo_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.item_repo_spring.models.Type;

import java.util.*;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer>{
    @Query("SELECT t.id AS typeId, t.name AS typeName, " +
    "COUNT(DISTINCT st.id) AS sumSubTypesQuantity, " +  // Count distinct subtypes under each type
    "SUM(i.quantity) AS sumItemsQuantity " +            // Sum of all item quantities under subtypes of this type
    "FROM Type t " +
    "LEFT JOIN t.subTypes st " +
    "LEFT JOIN st.items i " +
    "GROUP BY t.id, t.name")
    List<Object[]> findTypeWithSubTypesAndItemsQuantities();
}


