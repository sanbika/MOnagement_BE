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
	public List<Item> getExpirItems(@RequestParam("date") LocalDate date){
		return itemService.getExpirItems(date);
	}

    @GetMapping("tobuy")
	public List<Item> getToBuyItems(@RequestParam("quantity") int quantity){
		return itemService.getToBuyItems(quantity);
	}

    @PostMapping("add")
    public void addNewItem(@RequestBody Item item){
        itemService.addNewItem(item);
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
    public void putMethodName(
        @RequestParam("id") Long id, 
        @RequestBody(required = true) Map<String, String> bodyContent) {

        itemService.updateItem(
            id, 
            bodyContent.get("name"), 
            bodyContent.get("expirDate"), 
            bodyContent.get("quantity"), 
            bodyContent.get("type")
            );
    }
    
}
