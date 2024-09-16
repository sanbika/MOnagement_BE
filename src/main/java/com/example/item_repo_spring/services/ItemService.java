package com.example.item_repo_spring.services;

import java.util.*;
import java.time.LocalDate;

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
	public List<Item> getExpiryItems(LocalDate date){
		return itemRepository.findByExpiryDateBeforeOrderByExpiryDateAsc(date);
	}

	//Create a new item
	@Transactional
    public void addNewItem(Map<String, String> bodyContent) {
		// check whether sub type exist.
		SubType itemSubType = subTypeRepository.findById(Integer.parseInt(bodyContent.get("sub_type_id")))
		.orElseThrow(() -> new IllegalStateException("Selected sub-type Id does not exist in database"));
	

		String itemName = bodyContent.get("name");
		LocalDate itemDate = LocalDate.parse(bodyContent.get("expiryDate"));
		Integer itemQuantity = Integer.parseInt( bodyContent.get("quantity"));

		// check whether item with specified name, expiry date, sub-subtype exist.
		Optional<Item> itemOptional = itemRepository.findByNameAndExpiryDateAndSubType_Id(itemName, itemDate, itemSubType.getId());

		if (itemOptional.isPresent()) {
			throw new IllegalStateException("Item with the same name, expiry date and sub-type already exists. Update quantity instead.");
		}

		// Create and save new Item
		Item item = new Item();
		item.setName(itemName);
		item.setExpiryDate(itemDate);
		item.setSubType(itemSubType);
		item.setQuantity(itemQuantity);
	
		// Save the Item
		itemRepository.save(item);


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
		String newExpiryDate,
		String newQuantity,
		String newSubTypeId)
		{
			// Ensure this item exists
			Optional<Item> itemOptional = itemRepository.findById(id);

			if (itemOptional.isPresent()){
				Item item = itemOptional.get();

				// Process input and set new values if not null
				String name = (newName != null && !newName.trim().isEmpty()) ? newName : item.getName();
				LocalDate expiryDate = (newExpiryDate != null && !newExpiryDate.trim().isEmpty()) ? LocalDate.parse(newExpiryDate) : item.getExpiryDate();
				Integer subTypeId = (newSubTypeId != null && !newSubTypeId.trim().isEmpty()) ? Integer.parseInt(newSubTypeId) : item.getSubType().getId();
				Integer quantity = (newQuantity != null && !newQuantity.trim().isEmpty()) ? Integer.parseInt(newQuantity) : item.getQuantity();

				// Ensure updated value does not conflict with records in DB(if either property of name/expiryDate/subType is modified)
				if (
					(newName != null && !newName.trim().isEmpty())
				 || (newExpiryDate != null && !newExpiryDate.trim().isEmpty()) 
				 || (newSubTypeId != null && !newSubTypeId.trim().isEmpty())
				 
				 ){
					Optional<Item> itemSamePropertyOptional = itemRepository.findByNameAndExpiryDateAndSubType_Id(
						name,
						expiryDate, 
						subTypeId);

						if (itemSamePropertyOptional.isPresent()) {
							throw new IllegalArgumentException("The same combination of name, expiry date and sub type exists");
						}
						else{
							
							 // Update item attributes if it has changed compared to original attribtue
							if (!Objects.equals(item.getName(), name)) {
								item.setName(name);
							}
				
							if (!Objects.equals(item.getExpiryDate(), expiryDate)) {
								item.setExpiryDate(expiryDate);
							}
				
							if (!Objects.equals(item.getSubType().getId(), subTypeId)) {
								SubType newSubType = subTypeRepository.findById(subTypeId)
									.orElseThrow(() -> new IllegalArgumentException("Invalid sub type ID: " + subTypeId));
								item.setSubType(newSubType);
							}

							if (!Objects.equals(item.getQuantity(), quantity)) {
								item.setQuantity(quantity);
							}
		
						}
				}
				else{
					//if only update quantity, no need to check conflict.
					if (!Objects.equals(item.getQuantity(), quantity)) {
						item.setQuantity(quantity);
					}
				}

				itemRepository.save(item);	
				
			}
							
			else{
				throw new EntityNotFoundException("Item not found with ID: " + id);
			}
		}

}
