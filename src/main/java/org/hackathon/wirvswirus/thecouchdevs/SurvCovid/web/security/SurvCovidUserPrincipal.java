package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.web.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.User;
/*
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
*/
public class SurvCovidUserPrincipal /*implements UserDetails*/ {

	private User user;
	
	public SurvCovidUserPrincipal(User user) {
		this.user = user;
	}
/*
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		final List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("User"));
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserName();
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
	
	*/
	
}
