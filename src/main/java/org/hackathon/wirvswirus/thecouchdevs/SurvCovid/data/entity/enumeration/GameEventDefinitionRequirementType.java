package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration;

public enum GameEventDefinitionRequirementType {

    HAS_HAPPENED("has_happened"),
    HAS_NOT_HAPPENED("has_not_happened");

    private final String text;

    GameEventDefinitionRequirementType(final String text){
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
