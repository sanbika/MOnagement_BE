package com.example.item_repo_spring.services;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.item_repo_spring.models.Item;
import com.example.item_repo_spring.repositories.ItemRepository;
import com.example.item_repo_spring.repositories.SubTypeRepository;

import jakarta.persistence.EntityNotFoundException;

import com.example.item_repo_spring.models.SubType;



@Service
public class ItemService {

	private final ItemRepository itemRepository;
	private final SubTypeRepository subTypeRepository;

	public ItemService(ItemRepository itemRepository, SubTypeRepository subTypeRepository){
		this.itemRepository = itemRepository;
		this.subTypeRepository = subTypeRepository;
	}

	//Get all items
	public List<Item> getItems(){
		return itemRepository.findAllItemsOrderedBySubType();
	}

	//Get an item
	public Item getItem(Long id){
		Optional <Item> itemOptional = itemRepository.findById(id);
		if (itemOptional.isPresent()){
			Item item = itemOptional.get();
			return item;
		}
		else{
			throw new IllegalStateException("Item does not exist.");
		}
	}

	//Get expire items
	public List<Item> getExpirItems(LocalDate date){
		return itemRepository.findByExpirDateBeforeOrderByExpirDateAsc(date);
	}

	//Create a new item
	@Transactional
    public void addNewItem(Item item) {
		Optional<Item> itemOptional = itemRepository.findByNameAndExpirDateAndSubType_Id(item.getName(), item.getExpirDate(), item.getSubType().getId());

		if (itemOptional.isPresent()) {
			throw new IllegalStateException("Item has already existed");
		}
		else {
			itemRepository.save(item);
		}
    }

	//Delete an item
	public void deleteItem(Long id){
		boolean existed = itemRepository.existsById(id);
		if (existed) {
			itemRepository.deleteById(id);
		}
		else{
			throw new EntityNotFoundException("Item not found with ID: " + id);
		}
	}

	//Delete items
	public void deleteItems(List<Long> ids){
		itemRepository.deleteAllById(ids);
	}

	//Update an item
	@Transactional
	public void updateItem(
		Long id, 
		String newName, 
		String newExpirDate,
		String newQuantity,
		String newSubTypeId)
		{
			// Ensure this item exists
			Optional <Item> itemOptional = itemRepository.findById(id);

			if (itemOptional.isPresent()){
				Item item = itemOptional.get();
	
				// Process input and set new values if not null
				String name = (newName != null && !newName.trim().isEmpty()) ? newName : item.getName();
				LocalDate expirDate = (newExpirDate != null && !newExpirDate.trim().isEmpty()) ? LocalDate.parse(newExpirDate) : item.getExpirDate();
				Integer quantity = (newQuantity != null && !newQuantity.trim().isEmpty()) ? Integer.parseInt(newQuantity) : item.getQuantity();
				Integer subTypeId = (newSubTypeId != null && !newSubTypeId.trim().isEmpty()) ? Integer.parseInt(newSubTypeId) : item.getSubType().getId();

				// Ensure updated value does not conflict with records in DB
				Optional<Item> itemSamePropertyOptional = itemRepository.findByNameAndExpirDateAndSubType_Id(
					name,
					expirDate, 
					subTypeId);
					
				if (itemSamePropertyOptional.isPresent()) {
					throw new IllegalArgumentException("The same combination of name, date and sub type exists");
				}
				else{
					
					 // Update item attributes if it has changed compared to original attribtue
					if (!Objects.equals(item.getName(), name)) {
						item.setName(name);
					}
		
					if (!Objects.equals(item.getExpirDate(), expirDate)) {
						item.setExpirDate(expirDate);
					}
		
					if (!Objects.equals(item.getQuantity(), quantity)) {
						item.setQuantity(quantity);
					}
		
					if (!Objects.equals(item.getSubType().getId(), subTypeId)) {
						SubType newSubType = subTypeRepository.findById(subTypeId)
							.orElseThrow(() -> new IllegalArgumentException("Invalid sub type ID: " + subTypeId));
						item.setSubType(newSubType);
					}
					
					itemRepository.save(item);

				}
			}
							
			else{
				throw new EntityNotFoundException("Item not found with ID: " + id);
			}
		}

}
