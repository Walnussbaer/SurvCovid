package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.ItemType;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemTypeService {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	public ItemTypeService(ItemRepository itemRepository) {
		if (itemRepository == null) {
			throw new NullPointerException("itemRepository cannot be null");
		}
		this.itemRepository = itemRepository;
	}
	
	public ItemType addItem(ItemType itemType) {
		
		if (itemType == null) {
			throw new NullPointerException("item cannot be null");
		}
		this.itemRepository.save(itemType);
		
		return itemType;
	}
	
	public List<ItemType> getAllItemTypes(){
		
		List<ItemType> itemTypes = (List<ItemType>) this.itemRepository.findAll();
		return itemTypes;
	}

}
