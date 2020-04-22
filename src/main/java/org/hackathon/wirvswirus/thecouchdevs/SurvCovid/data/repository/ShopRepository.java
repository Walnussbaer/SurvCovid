package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.Shop;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShopRepository extends CrudRepository<Shop,Long> {
    Shop findByUser(User user);
}

