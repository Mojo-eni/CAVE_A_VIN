package fr.eni.cave.security;

import org.apache.catalina.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private JwtAuthenticationFilter jwFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;
    /**
     * Restriction des URLs selon la connexion utilisateur et leurs rôles
     */
//    @Bean
//     public UserDetailsManager userDetailsManager(DataSource dataSource){
//        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
//        userDetailsManager.setUsersByUsernameQuery("SELECT login, password, 1 FROM CAV_USER WHERE login=?");
//        userDetailsManager.setAuthoritiesByUsernameQuery("SELECT login, authority FROM CAV_USER WHERE login=?");
//         return  userDetailsManager;
//     }

     @Bean
     SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
         httpSecurity.authorizeHttpRequests(auth -> {
             auth
                     //BOUTEILLES
                     .requestMatchers("/caveavin/bouteilles").permitAll()
                     .requestMatchers(HttpMethod.POST,"/auth").permitAll()
                     .requestMatchers(HttpMethod.GET, "/caveavin/bouteilles/**").hasAnyRole("CLIENT", "OWNER")
                     .requestMatchers(HttpMethod.POST, "/caveavin/bouteilles").hasRole("OWNER")
                     .requestMatchers(HttpMethod.PUT, "/caveavin/bouteilles/**").hasRole("OWNER")
                     //PANIERS
                     .requestMatchers(HttpMethod.GET, "/caveavin/paniers/**").hasAnyRole("CLIENT", "OWNER")
                     .requestMatchers(HttpMethod.PUT, "/caveavin/paniers/**").hasRole("CLIENT")
                     .requestMatchers(HttpMethod.POST, "/caveavin/paniers/**").hasRole("CLIENT")
                     // Toutes autres url et méthodes HTTP ne sont pas permises
                     .anyRequest().denyAll();
         });

         // configuration du jwtFilter

         // pas bien mais c'est une demo
         httpSecurity.csrf(csrf -> csrf.disable());

         httpSecurity.addFilterBefore(jwFilter, UsernamePasswordAuthenticationFilter.class);

         httpSecurity.authenticationProvider(authenticationProvider);

         // plus de possibilité de session.
         httpSecurity.sessionManagement(session -> {
             session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
         });
         return httpSecurity.build();
     }
}

