package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.ItemType;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.Shop;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.ShopItem;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.ShopItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ShopItemService {

    @Autowired
    private ShopItemRepository shopItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    public ShopItemService(ShopItemRepository shopItemRepository) {
        if (shopItemRepository == null)
            throw new NullPointerException("shopItemRepository cannot be null");
        this.shopItemRepository = shopItemRepository;
    }

    public ShopItem saveShopItem(ShopItem shopItem) {
        if (shopItem == null)
            throw new NullPointerException("shopItem cannot be null");
        return this.shopItemRepository.save(shopItem);
    }

    public List<ShopItem> getShopItems(Shop shop) {
        return this.shopItemRepository.findByShop(shop);
    }

    public boolean shopHasItemAmount(Shop shop, long itemTypeId, int itemAmount) {
        return (this.shopItemRepository.findByShopAndItemType_ItemTypeIdIsAndItemCountGreaterThanEqual(shop, itemTypeId, itemAmount) != null);
    }

    public boolean shopItemRemove(Shop shop, ItemType itemType, int itemAmount) {
        // TODO: Implement
        ShopItem shopItem = this.shopItemRepository.findByShopAndItemType(shop, itemType);

        if(shopItem == null) {
            // Item isn't offered at all
            // TODO: Add exception handling
            return false;
        }

        if(shopItem.getItemCount() < itemAmount) {
            // There aren't enough items
            // TODO: Add exception handling
            return false;
        }

        shopItem.setItemCount(shopItem.getItemCount() - itemAmount);

        // Check if there are any copies of this item type left
        if(shopItem.getItemCount() == 0) {
            // No copies of this item type left, so we delete the entry from the database
            this.shopItemRepository.delete(shopItem);
            return true;
        }

        this.shopItemRepository.save(shopItem);

        return true;
    }

}
