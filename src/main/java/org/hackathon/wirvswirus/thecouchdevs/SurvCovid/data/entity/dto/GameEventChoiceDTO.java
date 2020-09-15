package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.dto;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.GameEventImpact;

public class GameEventChoiceDTO {

    private Long id;
    private String data;

    private String description;

    // TODO: Implement
    // private GameEventImpact impact;

    public GameEventChoiceDTO() {

    }

    public GameEventChoiceDTO(Long id, String description, String data) {
        this.id = id;
        this.description = description;
        this.data = data;
    }

    public GameEventChoiceDTO(String description, String data) {
        this.id = -1L;
        this.description = description;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
