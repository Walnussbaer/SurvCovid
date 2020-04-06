package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.Inventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InventoryRepository extends CrudRepository<Inventory,Long> {

}

