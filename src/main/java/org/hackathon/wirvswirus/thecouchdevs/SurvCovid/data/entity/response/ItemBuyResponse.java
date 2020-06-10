package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.ShopItem;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.ItemBuyStatus;

import java.util.List;

public class ItemBuyResponse extends SurvCovidBaseResponse {

    private ItemBuyStatus itemBuyStatus;

    public ItemBuyResponse(String message, ItemBuyStatus itemBuyStatus) {
        super(message);
        this.itemBuyStatus = itemBuyStatus;
    }

    public ItemBuyStatus getItemBuyStatus() {
        return itemBuyStatus;
    }

    public void setItemBuyStatus(ItemBuyStatus itemBuyStatus) {
        this.itemBuyStatus = itemBuyStatus;
    }
}
