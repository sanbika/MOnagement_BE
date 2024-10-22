package com.example.item_repo_spring.services;

import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties.System;
import org.springframework.stereotype.Service;
import org.springframework.transaction.IllegalTransactionStateException;

import com.example.item_repo_spring.models.SubType;
import com.example.item_repo_spring.models.Type;

import com.example.item_repo_spring.repositories.TypeRepository;

import jakarta.transaction.Transactional;

import com.example.item_repo_spring.repositories.SubTypeRepository;

import java.util.*;


@Service
public class SubTypeService {
    private final SubTypeRepository subTypeRepository;
    private final TypeRepository typeRepository;

    public SubTypeService(SubTypeRepository subTypeRepository, TypeRepository typeRepository) {
        this.subTypeRepository = subTypeRepository;
        this.typeRepository = typeRepository;
    }


    public List<SubType> getSubTypes(){
        return subTypeRepository.findAll();
    }

     // Method to get subtypes with total item quantities less than a certain value
    public List<Map<String, Object>> getSubTypesWithLimitedItemQuantities(int maxQuantity) {
        List<Object[]> results = subTypeRepository.findSubTypesWithLimitedItemQuantities(maxQuantity);
        List<Map<String, Object>> formattedResults = new ArrayList<>();

        // Map returned data into dict
        for (Object[] result:results){
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("id", result[0]);
            resultMap.put("name", result[1]);
            resultMap.put("sum", result[2]);
            formattedResults.add(resultMap);
        }
        return formattedResults;
    }

    // Method to get subtypes with total item quantities and type
    public List<Map<String, Object>>findSubTypesWithItemQuantities(){
        List<Object[]> results = subTypeRepository.findSubTypesWithItemQuantities();
        List<Map<String, Object>> formattedResults = new ArrayList<>();

        // Map returned data into dict
        for (Object[] result:results){
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("id", result[0]);
            resultMap.put("name", result[1]);
            
            Type type = (Type) result[2];
            resultMap.put("type", type);
            Long sumItemsQuantity =  result[3]!=null? (Long) result[3]:0;
            resultMap.put("sumItemsQuantity", sumItemsQuantity);
            formattedResults.add(resultMap);
        }
        return formattedResults;
    }

    public SubType getSubType(Integer id){
        Optional <SubType> subTypeOptional = subTypeRepository.findById(id);
        if (subTypeOptional.isPresent()){
            return subTypeOptional.get();
        }
        else {
            throw new IllegalTransactionStateException("Sub type does not existed");
        }
    }

    

    @Transactional
    public void addNewSubType(Map<String, String> bodyContent){
        try {
            
            Optional <Type> typeOptional = typeRepository.findById(Integer.parseInt(bodyContent.get("type")));
        
            if (typeOptional.isPresent()){
                SubType subType = new SubType();
                subType.setName(bodyContent.get("name"));
                subType.setType(typeOptional.get());
                subTypeRepository.save(subType);
            }
            else{
                throw new IllegalStateException("type not exist");
            }          
            
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Transactional
    public void deleteSubType(Integer id){
        try {
            subTypeRepository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalTransactionStateException(e.getMessage());
        }
    }

    public void deleteSubTypes(List<Integer> ids){
        try {
            subTypeRepository.deleteAllById(ids);
            
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public void updateSubType(Integer id, String newName, String newTypeId){
        Optional <SubType> subTypeOptional = subTypeRepository.findById(id);
        if (subTypeOptional.isPresent()){
            SubType subType = subTypeOptional.get();

            // Process input and set new values if not null
            String name = (newName != null && !newName.trim().isEmpty()) ? newName : subType.getName();
            Integer typeId = (newTypeId != null && !newTypeId.trim().isEmpty()) ?  Integer.parseInt(newTypeId) : subType.getType().getId();
            
            // If not the same as value in DB, then update that field
            if (!Objects.equals(subType.getName(), name)){
                subType.setName(newName);               
            }

            if (!Objects.equals(subType.getType().getId(), typeId)){
                Optional <Type> typeOptional = typeRepository.findById(typeId);
                if (typeOptional.isPresent()){
                    Type type = typeOptional.get();
                    subType.setType(type);
                }
                else {
                    throw new IllegalTransactionStateException("Type not existed");
                }
            }
            
            subTypeRepository.save(subType);
        }
        
        else{
            throw new IllegalTransactionStateException("Sub type not existed");
        }

    }
   
}
