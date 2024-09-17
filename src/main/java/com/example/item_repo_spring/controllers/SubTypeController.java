package com.example.item_repo_spring.controllers;
import java.util.*;

import org.springframework.web.bind.annotation.*;

import com.example.item_repo_spring.services.SubTypeService;
import com.example.item_repo_spring.models.SubType;




@RestController
@RequestMapping(path = "api/subtype")
@CrossOrigin(origins = "*")
public class SubTypeController {
    private final SubTypeService subTypeService;

    public SubTypeController(SubTypeService subTypeService) {
        this.subTypeService = subTypeService;
    }

    @GetMapping
    public List<SubType> getSubTypes() {
        return subTypeService.getSubTypes();
    }

    @GetMapping("find")
    public SubType getSubType(@RequestParam("id") Integer id){
        return subTypeService.getSubType(id);
    }

    @GetMapping("tobuy")
    public List<Map<String, Object>> getToBuySubTypes(@RequestParam("quantity") Integer quantity) {
        return subTypeService.getSubTypesWithLimitedItemQuantities(quantity);

    }

    @PostMapping("add")
    public void addNewSubType(@RequestBody(required = true) Map<String, String> bodyContent){
        subTypeService.addNewSubType(bodyContent);
    }

    @DeleteMapping("delete")
    public void deleteItem(@RequestParam("id") Integer id){
        subTypeService.deleteSubType(id);
    }

    @DeleteMapping("delete/multiple")
    public void deleteSubTypes(@RequestBody(required = true) Map<String, List<Integer>> bodyContent){
        subTypeService.deleteSubTypes(bodyContent.get("ids"));
    }

    @PatchMapping("update")
    public void updateSubType(
        @RequestParam("id") Integer id,
        @RequestBody(required = true) Map<String, String> bodyContent){

            subTypeService.updateSubType(id, bodyContent.get("name"));
        }
    
    
}
