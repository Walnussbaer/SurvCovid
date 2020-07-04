package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.response;

import java.time.LocalDateTime;
import java.util.List;

public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private LocalDateTime lastlogin;
    private List<String> roles;
    private boolean isActiveAccount;

    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles, LocalDateTime lastlogin, boolean isActiveAccount) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.lastlogin = lastlogin;
        this.isActiveAccount = isActiveAccount;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public LocalDateTime getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(LocalDateTime lastlogin) {
        this.lastlogin = lastlogin;
    }

    public boolean isActiveAccount() {
        return isActiveAccount;
    }

    public void setActiveAccount(boolean activeAccount) {
        isActiveAccount = activeAccount;
    }
}
