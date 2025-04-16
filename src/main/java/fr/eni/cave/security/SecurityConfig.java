package fr.eni.cave.security;

import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
     public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery("SELECT pseudo, password, 1 FROM CAV_USER WHERE pseudo=?");
        userDetailsManager.setAuthoritiesByUsernameQuery("SELECT pseudo, authority FROM CAV_USER WHERE pseudo=?");
         return  userDetailsManager;
     }

     @Bean
     SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
         httpSecurity.authorizeHttpRequests(auth -> {
             auth
                     .requestMatchers("/caveavin/bouteilles").permitAll()
                     .requestMatchers(HttpMethod.GET, "/caveavin/bouteilles/**").hasAnyRole("CLIENT", "ADMIN")
                     .requestMatchers(HttpMethod.POST, "/caveavin/bouteilles").hasAnyRole("CLIENT", "ADMIN")
                     .requestMatchers(HttpMethod.PUT, "/caveavin/bouteilles/**").hasAnyRole("CLIENT", "ADMIN")
                     .anyRequest().denyAll();
         });

         httpSecurity.httpBasic(Customizer.withDefaults());
         return httpSecurity.build();
     }
}

