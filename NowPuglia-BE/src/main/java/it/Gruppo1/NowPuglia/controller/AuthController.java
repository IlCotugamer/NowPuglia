package it.Gruppo1.NowPuglia.controller;

import it.Gruppo1.NowPuglia.dto.ChangePlanDto;
import it.Gruppo1.NowPuglia.dto.PasswordResetDto;
import it.Gruppo1.NowPuglia.dto.UtentiLoginDto;
import it.Gruppo1.NowPuglia.dto.UtentiRegisterDto;
import it.Gruppo1.NowPuglia.model.UtentiModel;
import it.Gruppo1.NowPuglia.repository.IAbbonamentiRepository;
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
    private final IAbbonamentiRepository iAbbonamentiRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, IUsersManagerService iUsersManagerService, IUtentiRepository iUtentiRepository, PasswordEncoder passwordEncoder, IAbbonamentiRepository iAbbonamentiRepository) {
        this.authenticationManager = authenticationManager;
        this.iUsersManagerService = iUsersManagerService;
        this.iUtentiRepository = iUtentiRepository;
        this.passwordEncoder = passwordEncoder;
        this.iAbbonamentiRepository = iAbbonamentiRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Validated UtentiRegisterDto utente) {
        if (iUtentiRepository.existsByEmail(utente.getEmail()))
            return ResponseEntity.badRequest().body("Email già in uso");
        iUsersManagerService.userRegistration(utente);
        return ResponseEntity.ok("Registrazione completata con successo");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Validated UtentiLoginDto utenteDto, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("QUESTO è UTENTI DTO LOGIN "+ utenteDto);
        UtentiModel utente = iUtentiRepository.findByEmail(utenteDto.getEmail());
        if (utente != null && passwordEncoder.matches(utenteDto.getPassword(), utente.getPassword())) {
            Collection<? extends GrantedAuthority> authorities = DataBaseUserDetailsService.getAuthorities(utente);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(utenteDto.getEmail(), utenteDto.getPassword(), authorities);
            token.setDetails(utenteDto);
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContext context = securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(auth);
            securityContextHolderStrategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);
            return ResponseEntity.ok("Accesso completato con successo");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email o password errati");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        SecurityContext context = SecurityContextHolder.getContext();
        request.getSession().invalidate();
        context.setAuthentication(null);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout completato");
    }

    @PostMapping("/passwordReset")
    public ResponseEntity<String> changePassword(@RequestBody @Validated PasswordResetDto passwordResetDto) {
        if (passwordResetDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Richiesta non valida");
        }
        String currentPassword = passwordResetDto.getCurrentPassword();
        String newPassword = passwordResetDto.getNewPassword();
        String confirmPassword = passwordResetDto.getConfirmPassword();
        if (currentPassword == null || newPassword == null || confirmPassword == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le password non possono essere nulle");
        }
        UtentiModel utentiModel = iUtentiRepository.findByEmail(iUsersManagerService.getUsernameFromToken());
        if (utentiModel != null) {
            if (passwordEncoder.matches(currentPassword, utentiModel.getPassword())) {
                if (newPassword.equals(confirmPassword)) {
                    iUsersManagerService.passwordReset(
                            iUsersManagerService.getUsernameFromToken(),
                            newPassword,
                            passwordEncoder
                    );
                } else
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Le password non coincidono");
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password errata");
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non valido");
        return ResponseEntity.ok("Password cambiata con successo");
    }

    @PostMapping("/changePlan")
    public ResponseEntity<String> changePlan(@RequestBody @Validated ChangePlanDto changePlanDto) {
        UtentiModel utentiModel = iUtentiRepository.findByEmail(iUsersManagerService.getUsernameFromToken());
        if (utentiModel != null) {
            if (iAbbonamentiRepository.findById(changePlanDto.getPlanNumber()) != null) {
                utentiModel.setAbbonamentoInfo(iAbbonamentiRepository.findById(changePlanDto.getPlanNumber()));
                iUtentiRepository.save(utentiModel);
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Abbonamento non valido");
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non valido");
        return ResponseEntity.ok("Password cambiata con successo");
    }
}
