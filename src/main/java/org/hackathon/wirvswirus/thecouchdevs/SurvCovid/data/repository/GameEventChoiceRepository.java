package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventChoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameEventChoiceRepository extends CrudRepository<GameEventChoice, Long> {
    


}
