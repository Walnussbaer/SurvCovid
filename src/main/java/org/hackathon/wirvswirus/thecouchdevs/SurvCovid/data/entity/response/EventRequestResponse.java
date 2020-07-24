package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEvent;

public class EventRequestResponse extends SurvCovidBaseResponse {

    public GameEvent gameEvent;

    public EventRequestResponse(String message, GameEvent gameEvent) {
        super(message);
        this.gameEvent = gameEvent;
    }
}
