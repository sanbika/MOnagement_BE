package com.example.item_repo_spring.services;

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
    public List<Object[]> getSubTypesWithLimitedItemQuantities(int maxQuantity) {
        return subTypeRepository.findSubTypesWithLimitedItemQuantities(maxQuantity);
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
            
            Optional <Type> typeOptional = typeRepository.findById(Integer.parseInt(bodyContent.get("type_id")));
        
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

    public void updateSubType(Integer id, String name){
        boolean existed = subTypeRepository.existsById(id);
        // System.out.println(existed);
        SubType subType = subTypeRepository.getReferenceById(id);
        if (!Objects.equals(subType.getName(), name) && name.length() > 0){
            subType.setName(name);
            subTypeRepository.save(subType);
        }
    }
   
}
