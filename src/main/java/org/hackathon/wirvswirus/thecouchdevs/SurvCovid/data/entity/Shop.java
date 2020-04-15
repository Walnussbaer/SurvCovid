package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Shop")
@Table(name="SHOP")
//@JsonIdentityInfo(
//		generator = ObjectIdGenerators.PropertyGenerator.class,
//		property = "shopId")
public class Shop {
	/** The stock of a virtual shop, assigned to a user.
	 *
	 */

	@Id
	@Column(name="SHOP_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long shopId;

	@OneToOne
	@JoinColumn(name = "USER_ID", insertable = true, updatable = false)
	private User user;

	@OneToMany
	@JoinColumn(name = "SHOP_ID", insertable = true, updatable = false)
	//@JsonManagedReference
	private List<ShopItem> shopItems;

	@Column(name="CREATION_TIME")
	@DateTimeFormat(pattern="dd.MM.yyyy")
	LocalDateTime creationTime = LocalDateTime.now();

	public Shop() {}

	public Shop(User user) {
		this.user = user;
		this.creationTime = LocalDateTime.now();
	}

	public long getShopId() {
		return this.shopId;
	}

	public List<ShopItem> getShopItems() {
		return shopItems;
	}
}
