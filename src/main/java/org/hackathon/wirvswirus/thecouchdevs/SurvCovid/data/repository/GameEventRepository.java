package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository;


import java.util.List;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEvent;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameEventRepository extends CrudRepository<GameEvent,Long>{
    
    GameEvent findByIsDoneAndPlayer(boolean isDone, User player);

}
