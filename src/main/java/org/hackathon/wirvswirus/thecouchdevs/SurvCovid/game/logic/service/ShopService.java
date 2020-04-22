package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.*;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    public ShopService(ShopRepository shopRepository) {
        if (shopRepository == null)
            throw new NullPointerException("shopRepository cannot be null");
        this.shopRepository = shopRepository;
    }

    public Shop getShop(User user) {
        return this.shopRepository.findByUser(user);
    }

    public Shop saveShop(Shop shop) {
        if (shop == null)
            throw new NullPointerException("shop cannot be null");
        return this.shopRepository.save(shop);
    }

}
