package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import javax.persistence.*;
import java.util.*;

@Entity(name = "Item")
@Table(name = "ITEM",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "ITEM_ID"),
        @UniqueConstraint(columnNames = "ITEM_NAME") })
public class Item {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long itemNumber;

    @Column(name = "ITEM_ID")
    private int itemId;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @ManyToMany(mappedBy = "items", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Inventory> inventories;

//    @Column(name = "ITEM_TYPE")
//    @Enumerated(EnumType.STRING)
//    private ItemType itemType;

    public Item() {}

    public Item(int itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

}
