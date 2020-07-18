package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a logged in user. This class provides object instances which get sent back to the frontend. 
 */
public class SurvCovidUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String userName;

    private String email;

    @JsonIgnore
    private String password;

    private LocalDateTime lastLogin;

    private Collection<? extends GrantedAuthority> authorities;

    public SurvCovidUserDetails(Long id, String userName, String email, String password, LocalDateTime lastLogin,
                                Collection<? extends GrantedAuthority> authorities){

        this.id=id;
        this.userName=userName;
        this.email=email;
        this.password=password;
        this.lastLogin = lastLogin;
        this.authorities=authorities;

    }

    public static SurvCovidUserDetails build(User user){

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role-> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new SurvCovidUserDetails(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                user.getUserState().getLastLogin(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SurvCovidUserDetails user = (SurvCovidUserDetails) o;
        return Objects.equals(id, user.id);
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

}
