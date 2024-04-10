package it.Gruppo1.NowPuglia.controller;

import it.Gruppo1.NowPuglia.dto.UtentiDto;
import it.Gruppo1.NowPuglia.model.UtentiModel;
import it.Gruppo1.NowPuglia.repository.IUtentiRepository;
import it.Gruppo1.NowPuglia.service.IUsersManagerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private final IUsersManagerService iUsersManagerService;
    private final IUtentiRepository iUtentiRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(IUsersManagerService iUsersManagerService, IUtentiRepository iUtentiRepository, PasswordEncoder passwordEncoder) {
        this.iUsersManagerService = iUsersManagerService;
        this.iUtentiRepository = iUtentiRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Validated UtentiDto utenti, HttpServletRequest request, HttpServletResponse response) {
        if (iUtentiRepository.findByUsername(utenti.getUsername())) {
            return ResponseEntity.badRequest().body("Username già in uso");
        }

        UtentiModel utentiModel = new UtentiModel(
                utenti.getNome(),
                utenti.getCognome(),
                utenti.getDataNascita(),
                0,
                utenti.getUsername(),
                passwordEncoder.encode(utenti.getPassword())
        );

        iUtentiRepository.save(utentiModel);
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken
                .unauthenticated(
                        utentiModel.getUsername(),
                        utentiModel.getPassword()
                );

        Authentication authentication = authenticationManager.authenticate(token);
        return ResponseEntity.ok("Registrazione completata con successo");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UtentiModel utentiModel) {
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
