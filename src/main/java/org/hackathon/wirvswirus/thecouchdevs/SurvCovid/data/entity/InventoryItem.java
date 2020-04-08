package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import javax.persistence.*;
import java.util.*;

@Entity(name = "Inventory")
@Table(name="INVENTORY")
public class InventoryItem {

	@EmbeddedId
	private InventoryItemId id;

	@ManyToOne
	@JoinColumn(name = "ITEMTYPE_ID", insertable = false, updatable = false)
	private ItemType itemType;

	@ManyToOne
	@JoinColumn(name = "USER_ID", insertable = false, updatable = false)
	private User user;

	@Column(name="ITEM_COUNT")
	private int itemCount;

	public InventoryItem() {}

	public InventoryItem(User user, ItemType itemType, int itemCount) {
		this.user = user;
		this.itemType = itemType;
		this.itemCount = itemCount;

		this.id = new InventoryItemId(user.getUserNumber(), itemType.getItemTypeId());
	}

	public long getUserNumber() {
		return this.user.getUserNumber();
	}

	public String getUserName() {
		return this.user.getUserName();
	}

	public long getItemTypeId() {
		return this.itemType.getItemTypeId();
	}

	public String getItemTypeName() {
		return this.itemType.getItemTypeName();
	}

	public int getItemCount() {
		return this.itemCount;
	}

	public void addItemCount(int itemCount) {
		this.itemCount += itemCount;
	}

}
