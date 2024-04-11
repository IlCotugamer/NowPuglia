package it.Gruppo1.NowPuglia.controller;

import it.Gruppo1.NowPuglia.dto.UtentiDto;
import it.Gruppo1.NowPuglia.model.UtentiModel;
import it.Gruppo1.NowPuglia.repository.IUtentiRepository;
import it.Gruppo1.NowPuglia.service.IUsersManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final IUsersManagerService iUsersManagerService;
    private final IUtentiRepository iUtentiRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, IUsersManagerService iUsersManagerService, IUtentiRepository iUtentiRepository) {
        this.authenticationManager = authenticationManager;
        this.iUsersManagerService = iUsersManagerService;
        this.iUtentiRepository = iUtentiRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Validated UtentiDto utenti) {
        if (iUtentiRepository.existsByUsername(utenti.getUsername())) {
            return ResponseEntity.badRequest().body("Username già in uso");
        }

        UtentiModel utentiModel = new UtentiModel(
                utenti.getNome(),
                utenti.getCognome(),
                utenti.getDataNascita(),
                0, //0 = FREE (SOLO WEB), 1 = Studente o Ricercatore (MAX WEB USAGE AND 1 DEVICE 100 CALL API FOR HOUR, 2 = Azienda (MAX API USAGE)
                utenti.getUsername(),
                utenti.getPassword()
        );

        iUtentiRepository.save(utentiModel);

        return ResponseEntity.ok("Registrazione completata con successo");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Validated UtentiDto utentiDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(utentiDto.getUsername(), utentiDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("Accesso completata con successo");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody UtentiModel utentiModel) {
        return ResponseEntity.ok("Sessione terminata con successo");
    }

    @PostMapping("/passwordReset")
    public ResponseEntity<String> changePassword(@RequestBody @Validated UtentiModel utentiModel) {
        return ResponseEntity.ok("Password cambiata con successo");
    }

    @PostMapping("/changePlan")
    public ResponseEntity<String> changePlan(@RequestBody @Validated UtentiModel utentiModel) {
        return ResponseEntity.ok("Password cambiata con successo");
    }
}
