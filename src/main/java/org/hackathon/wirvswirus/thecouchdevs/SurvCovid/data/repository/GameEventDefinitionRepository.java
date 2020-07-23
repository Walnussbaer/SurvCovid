package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventDefinition;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameEventDefinitionRepository extends CrudRepository<GameEventDefinition, Long> {
    @Query(value = "SELECT def_target.* " +
            "FROM GAME_EVENT evt " +
            "INNER JOIN GAME_EVENT_DEFINITION def_past " +
            "  ON def_past.ID = evt.GAME_EVENT_DEFINITION_ID " +
            "INNER JOIN GAME_EVENT_REQUIREMENTS req " +
            "  ON def_past.ID = req.REQUIRED_GAME_EVENT_DEFINITION_ID " +
            "  INNER JOIN GAME_EVENT_DEFINITION def_target " +
            "  ON def_target.ID = req.TARGET_GAME_EVENT_DEFINITION_ID " +
            "WHERE 1=1 " +
            "  AND " +
               // Specific player"
            "  evt.PLAYER = (SELECT USER_ID FROM USER WHERE USER_ID = ?1) " +
            "  AND " +
            "  evt.IS_DONE = TRUE " +
            "  AND " +
            "  ( " +
            "    ( " +
                   // HAS_HAPPENED
            "      req.REQUIREMENT_TYPE = 1 " +
            "      AND " +
            "      ( " +
            "        req.GAME_EVENT_CHOICE_ID IS NULL " +
            "        OR " +
            "        req.GAME_EVENT_CHOICE_ID = evt.CHOSEN_CHOICE " +
            "      ) " +
            "    ) " +
            "    OR " +
            "    ( " +
                   // HAS_NOT_HAPPENED
            "      req.REQUIREMENT_TYPE = 2 " +
            "      AND " +
            "      ( " +
            "        req.GAME_EVENT_CHOICE_ID IS NULL " +
            "        OR " +
            "        req.GAME_EVENT_CHOICE_ID != evt.CHOSEN_CHOICE " +
            "      ) " +
            "    ) " +
            "  ) ",
            nativeQuery = true)
    public List<GameEventDefinition> findPossibleEventsForPlayer(Long userId);
}
