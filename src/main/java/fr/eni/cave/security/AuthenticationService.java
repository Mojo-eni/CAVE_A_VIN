package fr.eni.cave.security;

import fr.eni.cave.dal.UtilisateurRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class AuthenticationService {

    private UtilisateurRepository userRepository;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    public String authenticate(AuthenticationRequestDto requestDto){
        log.info("Authentification de l'utilisateur {}", requestDto.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getLogin(),
                        requestDto.getPassword()
                )
        );
        log.info("Authentification réussie pour l'utilisateur {}", requestDto.getLogin());
        return jwtService.generateToken(
                    userRepository.findById(requestDto.getLogin())
                        .orElseThrow(() -> new RuntimeException("User not found"))
        );
    }
}
