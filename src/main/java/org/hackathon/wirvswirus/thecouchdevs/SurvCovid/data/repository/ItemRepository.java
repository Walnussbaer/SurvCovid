package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.ItemType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemRepository extends CrudRepository<ItemType,Long> {
    // TODO: Dummy, replaced later
    List<ItemType> getTopByItemTypeNameNotNull();
    ItemType getDistinctByItemTypeId(long itemTypeId);
}

