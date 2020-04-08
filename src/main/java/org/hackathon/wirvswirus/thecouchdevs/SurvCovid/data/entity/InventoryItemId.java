package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InventoryItemId implements Serializable {
    /** Combined Primary Key for InventoryItem table
     *  Elements:
     *    userId
     *    itemTypeId
     */
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "ITEMTYPE_ID")
    private Long itemTypeId;

    public InventoryItemId() {
    }

    public InventoryItemId(Long userId, Long itemTypeId) {
        this.userId = userId;
        this.itemTypeId = itemTypeId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Long getItemTypeId() {
        return this.itemTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventoryItemId)) return false;
        InventoryItemId that = (InventoryItemId) o;
        return Objects.equals(getUserId(), that.getUserId()) &&
                Objects.equals(getItemTypeId(), that.getItemTypeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getItemTypeId());
    }
}
