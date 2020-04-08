package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration;

public enum GameEventDefinitionType {
	
	STORY_EVENT("story_event"),
	GENERIC_EVENT("generic_event");
	
	private final String text;
	
	GameEventDefinitionType(final String text){
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}
	

}
