package com.example.item_repo_spring.controllers;
import java.util.*;
import com.example.item_repo_spring.models.Item;
import com.example.item_repo_spring.services.ItemService;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;


@RestController
@RequestMapping(path = "api/item")
@CrossOrigin(origins = "*")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

	@GetMapping
	public List<Item> getItems(){
		return itemService.getItems();
	}

    @GetMapping("find")
	public Item getItem(@RequestParam("id") Long id){
		return itemService.getItem(id);
	}

    @GetMapping("expire")
	public List<Item> getExpiryItems(@RequestParam("date") LocalDate date){
		return itemService.getExpiryItems(date);
	}

    @PostMapping("add")
    public void addNewItem(@RequestBody(required = true) Map<String, String> bodyContent){
        itemService.addNewItem(bodyContent);
    }

    @DeleteMapping("delete")
    public void deleteItem(@RequestParam("id") Long id){
        itemService.deleteItem(id);
    }

    @DeleteMapping("delete/multiple")
    public void deleteItems(@RequestBody(required = true) Map<String, List<Long>> bodyContent){
        itemService.deleteItems(bodyContent.get("ids"));
    }

    @PatchMapping("update")
    public void updateItem(
        @RequestParam("id") Long id, 
        @RequestBody(required = true) Map<String, String> bodyContent) {

        itemService.updateItem(
            id, 
            bodyContent.get("name"), 
            bodyContent.get("expiryDate"), 
            bodyContent.get("quantity"), 
            bodyContent.get("subType")
            );
    }
    
}
