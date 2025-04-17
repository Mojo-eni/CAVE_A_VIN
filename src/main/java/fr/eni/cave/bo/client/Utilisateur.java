package fr.eni.cave.bo.client;

import fr.eni.cave.security.UserInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "CAV_USER")
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur implements UserDetails {

	@Id
	@Column(name = "LOGIN", nullable = false, length = 255)
	@EqualsAndHashCode.Include
	private String pseudo;
	
	@ToString.Exclude
	@Column(name = "PASSWORD", nullable = false, length = 68)
	private String password;
	
	@Column(name = "LAST_NAME", nullable = false, length = 90)
	private String nom;
	
	@Column(name = "FIRST_NAME", nullable = false, length = 150)
	private String prenom;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<UserInfo> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities.stream()
				.map(UserInfo::getAuthority)
				.map(SimpleGrantedAuthority::new)
				.toList();
	}

	@Override
	public String getUsername() {
		return pseudo;
	}

	@Override
	public boolean isAccountNonExpired() {
		return UserDetails.super.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return UserDetails.super.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return UserDetails.super.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return UserDetails.super.isEnabled();
	}
}