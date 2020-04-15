package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;

@Entity(name = "ShopItem")
@Table(name="SHOP_ITEM")
public class ShopItem {
	/** Item in the stock of a shop.
	 *
	 */

	@Id
	@Column(name="SHOP_ITEM_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long shopItemId;

	@ManyToOne
	@JoinColumn(name = "SHOP_ID", insertable = true, updatable = false)
	//@JsonBackReference
	@JsonIdentityReference(alwaysAsId = true)
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "shopId")
	@JsonProperty("shopId")
	private Shop shop;

	@ManyToOne
	@JoinColumn(name = "ITEMTYPE_ID", insertable = true, updatable = false)
	private ItemType itemType;

	@Column(name="ITEM_COUNT")
	private int itemCount;

	@Column(name="ITEM_PRICE")
	private double itemPrice;

	public ShopItem() {}

	public ShopItem(Shop shop, ItemType itemType, int itemCount, double itemPrice) {
		this.shop = shop;
		this.itemType = itemType;
		this.itemCount = itemCount;
		this.itemPrice = itemPrice;
	}

	public long getShopItemId() {
		return shopItemId;
	}

	public Shop getShop() {
		return shop;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public double getItemPrice() {
		return itemPrice;
	}
}
