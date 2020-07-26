package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response;

public class SurvCovidBaseResponse {
	
    private String message;
    
    public SurvCovidBaseResponse() {}

    public SurvCovidBaseResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
