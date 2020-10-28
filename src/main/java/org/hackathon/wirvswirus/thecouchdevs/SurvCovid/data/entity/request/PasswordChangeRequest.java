package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.request;

import javax.validation.constraints.NotBlank;

public class PasswordChangeRequest {

    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String newPassword_2;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword_2() {
        return newPassword_2;
    }

    public void setNewPassword_2(String newPassword_2) {
        this.newPassword_2 = newPassword_2;
    }
}
