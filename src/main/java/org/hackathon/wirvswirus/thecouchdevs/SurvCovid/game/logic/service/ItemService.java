package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.Item;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	public ItemService(ItemRepository itemRepository) {
		if (itemRepository == null) {
			throw new NullPointerException("itemRepository cannot be null");
		}
		this.itemRepository = itemRepository;
	}
	
	public Item addItem(Item item) {
		
		if (item == null) {
			throw new NullPointerException("item cannot be null");
		}
		this.itemRepository.save(item);
		
		return item;
	}
	
	public List<Item> getAllItems(){
		
		List<Item> items = (List<Item>) this.itemRepository.findAll();
		return items;
	}

}
