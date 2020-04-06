package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import javax.persistence.*;
import java.util.*;

@Entity(name = "Inventory")
@Table(name="INVENTORY")
public class Inventory {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long inventoryNumber;

	@Column(name="INVENTORY_ID")
	private int inventoryId;

	//@OneToOne(mappedBy = "inventory", cascade = CascadeType.ALL)
	@OneToOne
	@MapsId
	private User user;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinTable(
			name = "INVENTORY_ITEMS",
			joinColumns = { @JoinColumn(name = "INVENTORY_ID") },
			inverseJoinColumns = { @JoinColumn(name = "ITEM_ID") }
	)
	List<Item> items = new ArrayList<>();

	public Inventory() {}

	public Inventory(User user) {
		this.user = user;
	}

	public void addItem(Item item) {
		this.items.add(item);
	}

	public List<Item> getItems() {
		return this.items;
	}
}
