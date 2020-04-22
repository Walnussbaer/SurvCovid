package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.ItemType;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.Shop;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.ShopItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ShopItemRepository extends CrudRepository<ShopItem,Long> {
    List<ShopItem> findByShop(Shop shop);
    List<ShopItem> findByShopAndItemType_ItemTypeIdIsAndItemCountGreaterThanEqual(Shop shop, long itemTypeId, int itemCount);
    ShopItem findByShopAndItemType(Shop shop, ItemType itemType);
}

