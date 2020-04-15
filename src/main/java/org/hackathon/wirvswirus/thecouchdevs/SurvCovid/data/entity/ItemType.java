package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import javax.persistence.*;
import java.util.*;

@Entity(name = "ItemType")
@Table(name = "ITEMTYPE",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "ITEMTYPE_ID"),
        @UniqueConstraint(columnNames = "ITEMTYPE_NAME") })
public class ItemType {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long itemNumber;

    @Column(name = "ITEMTYPE_ID")
    private long itemTypeId;

    @Column(name = "ITEMTYPE_NAME")
    private String itemTypeName;

    @Column(name = "ITEMTYPE_DISPLAYNAME")
    private String itemTypeDisplayName;

//    @ManyToMany(mappedBy = "itemTypes", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private Set<InventoryItem> inventories;

//    @Column(name = "ITEM_TYPE")
//    @Enumerated(EnumType.STRING)
//    private ItemType itemType;

    public ItemType() {}

    public ItemType(int itemTypeId, String itemTypeName, String itemTypeDisplayName) {
        this.itemTypeId = itemTypeId;
        this.itemTypeName = itemTypeName;
        this.itemTypeDisplayName = itemTypeDisplayName;
    }

    public long getItemTypeId() {
        return itemTypeId;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public String getItemTypeDisplayName() {
        return itemTypeDisplayName;
    }

}
