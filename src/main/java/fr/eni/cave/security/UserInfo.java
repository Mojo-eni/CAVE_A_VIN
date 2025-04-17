package fr.eni.cave.security;

import fr.eni.cave.bo.client.Utilisateur;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CAV_AUTHORITIES")
public class UserInfo {
    @Id
    @Column(name = "LOGIN", length = 255)
    private String login;

    @Column(name = "AUTHORITY", length = 250)
    private String authority;

    @ManyToOne
    @JoinColumn(name = "LOGIN", insertable = false, updatable = false)
    private Utilisateur user;
}
