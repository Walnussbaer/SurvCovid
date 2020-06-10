package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.request;

public class ItemBuyRequest {
    private long userId;
    private long itemTypeId;
    private int itemAmount;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(long itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public int getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }
}
