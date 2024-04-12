package it.Gruppo1.NowPuglia.controller;

import it.Gruppo1.NowPuglia.dto.UtentiLoginDto;
import it.Gruppo1.NowPuglia.dto.UtentiRegisterDto;
import it.Gruppo1.NowPuglia.model.UtentiModel;
import it.Gruppo1.NowPuglia.repository.IUtentiRepository;
import it.Gruppo1.NowPuglia.service.IUsersManagerService;
import it.Gruppo1.NowPuglia.serviceImp.DataBaseUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final IUsersManagerService iUsersManagerService;
    private final IUtentiRepository iUtentiRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, IUsersManagerService iUsersManagerService, IUtentiRepository iUtentiRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.iUsersManagerService = iUsersManagerService;
        this.iUtentiRepository = iUtentiRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Validated UtentiRegisterDto utenti) {
        if (iUtentiRepository.existsByUsername(utenti.getUsername()))
            return ResponseEntity.badRequest().body("Username già in uso");
        iUsersManagerService.userRegistration(utenti);
        return ResponseEntity.ok("Registrazione completata con successo");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Validated UtentiLoginDto utentiDto, HttpServletRequest request, HttpServletResponse response) {
        UtentiModel utente = iUtentiRepository.findByUsername(utentiDto.getUsername());
        if (utente != null) {
            Collection<? extends GrantedAuthority> authorities = DataBaseUserDetailsService.getAuthorities(utente);
            if (passwordEncoder.matches(utentiDto.getPassword(), utente.getPassword())) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(utentiDto.getUsername(), utentiDto.getPassword(), authorities);
                token.setDetails(utentiDto);
                Authentication auth = authenticationManager.authenticate(token);
                SecurityContext context = securityContextHolderStrategy.createEmptyContext();
                context.setAuthentication(auth);
                securityContextHolderStrategy.setContext(context);
                securityContextRepository.saveContext(context, request, response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username o password errati");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username o password errati");
        }
        return ResponseEntity.ok("Accesso completato con successo");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication().equals(authentication) && context.getAuthentication().getPrincipal() != null) {
            request.getSession().invalidate();
            context.setAuthentication(null);
            SecurityContextHolder.clearContext();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sessione non valida, utente non autenticato");
        }

        return ResponseEntity.ok("Logout completato");
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
