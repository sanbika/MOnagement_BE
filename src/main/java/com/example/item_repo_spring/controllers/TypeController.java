package com.example.item_repo_spring.controllers;

import java.util.List;
import java.util.Map;

import com.example.item_repo_spring.services.TypeService;
import com.example.item_repo_spring.models.Type;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(path="api/type")
@CrossOrigin(origins = "*")
public class TypeController {
    private final TypeService typeService;

    public TypeController(TypeService typeService){
        this.typeService = typeService;
    }

    @GetMapping
    public List<Type> getTypes() {
        return typeService.getTypes();
    }

    @GetMapping("/find")
    public Type getType(@RequestParam("id") Integer id) {
        return typeService.getType(id);
    }

    @PostMapping("/add")
    public void addNewType(@RequestBody Type type) {
        typeService.addNewType(type);
    }

    @PatchMapping("/update")
    public void putMethodName(
        @RequestParam("id") Integer id, 
        @RequestBody(required = true) Map<String, String> bodyContent) {
        
        typeService.updateType(id, bodyContent.get("name"));
    }
    
    @DeleteMapping("/delete")
    public void deletType(@RequestParam("id") Integer id){
        typeService.deleteType(id);
    }
    
}
