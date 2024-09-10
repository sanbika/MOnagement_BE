package com.example.item_repo_spring.services;

import java.util.List;
import java.util.Optional;
import java.util.Objects;

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
        Optional <Type> typeOptional = typeRepository.findTypeByName(type.getName());
        if (typeOptional.isPresent()){
            throw new IllegalStateException("Type name already exists");
        }
        else{
            typeRepository.save(type);
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
