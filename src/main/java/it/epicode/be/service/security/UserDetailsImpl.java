package it.epicode.be.service.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.epicode.be.model.security.Utente;
import lombok.Data;

@Data
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = -8990556584913973635L;
	private Long id;
	private String userName;
	private String email;
	@JsonIgnore
	private String password;
	private boolean isEnabled;
	private boolean accountNonLocked;
	private boolean accountNonExpired;
	private boolean credentialsNonExpired;
	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long id, String userName, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.accountNonLocked = true;
		this.accountNonExpired = true;
		this.credentialsNonExpired = true;
		this.isEnabled = true;
		this.authorities = authorities;

	}

	public static UserDetailsImpl build(Utente utente) {
		List<GrantedAuthority> authorities = utente.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList());
		return new UserDetailsImpl(utente.getId(), utente.getUserName(), utente.getEmail(), utente.getPassword(), authorities);
	}

	@Override
	public String getUsername() {
		return userName;
	}

}
