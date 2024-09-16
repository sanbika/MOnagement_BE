package com.example.item_repo_spring.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.IllegalTransactionStateException;

import com.example.item_repo_spring.models.SubType;
import com.example.item_repo_spring.repositories.SubTypeRepository;

import java.util.*;


@Service
public class SubTypeService {
    private final SubTypeRepository subTypeRepository;

    public SubTypeService(SubTypeRepository subTypeRepository) {
        this.subTypeRepository = subTypeRepository;
    }


    public List<SubType> getSubTypes(){
        return subTypeRepository.findAll();
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

    public void addNewSubType(SubType subType){
        try {
            subTypeRepository.save(subType);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public void deleteSubType(Integer id){
        boolean existed = subTypeRepository.existsById(id);
        if (existed) {
            subTypeRepository.deleteById(id);
        }
        else {
            throw new IllegalTransactionStateException("Sub type does not existed");
        }
    }

    public void updateSubType(Integer id, String name){
        boolean existed = subTypeRepository.existsById(id);
        System.out.println(existed);
        SubType subType = subTypeRepository.getReferenceById(id);
        if (!Objects.equals(subType.getName(), name) && name.length() > 0){
            subType.setName(name);
            subTypeRepository.save(subType);
        }
    }
    
}
