package com.example.item_repo_spring.services;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.IllegalTransactionStateException;

import com.example.item_repo_spring.repositories.TypeRepository;
import com.example.item_repo_spring.models.Type;

@Service
public class TypeService {

    private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository){
        this.typeRepository = typeRepository;
    }

    public List<Type> getTypes(){
        return typeRepository.findAll();
    }

    public List<Map<String,Object>> findTypeWithSubTypesAndItemsQuantities(){
        List<Object[]> results = typeRepository.findTypeWithSubTypesAndItemsQuantities();
        List<Map<String, Object>> formattedResults = new ArrayList<>();
        for (Object[] result:results){
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("id", result[0]);
            resultMap.put("name", result[1]);

            Long sumSubTypesQuantity =  result[2]!=null? (Long) result[2]:0;
            Long sumItemsQuantity =  result[3]!=null? (Long) result[3]:0;

            resultMap.put("sumSubTypesQuantity", sumSubTypesQuantity);
            resultMap.put("sumItemsQuantity", sumItemsQuantity);
            formattedResults.add(resultMap);
        }

        return formattedResults;
    }

    public Type getType(Integer id){
        Optional <Type> typeOptional = typeRepository.findById(id);
        if (typeOptional.isPresent()){
            return typeOptional.get();
        }
        else {
            throw new IllegalTransactionStateException("Type does not existed");
        }
    }

    public void addNewType(Type type){
        try {
            typeRepository.save(type);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public void deleteType(Integer id){
        boolean existed = typeRepository.existsById(id);
        if (existed) {
            typeRepository.deleteById(id);
        }
        else {
            throw new IllegalTransactionStateException("Type does not existed");
        }
    }

    public void updateType(Integer id, String name){
        boolean existed = typeRepository.existsById(id);
        System.out.println(existed);
        Type type = typeRepository.getReferenceById(id);
        if (!Objects.equals(type.getName(), name) && name.length() > 0){
            type.setName(name);
            typeRepository.save(type);
        }
    }
}
