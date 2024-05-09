package it.Gruppo1.NowPuglia.controller;

import it.Gruppo1.NowPuglia.config.ApiConfig.ApiResponse;
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
import org.springframework.http.MediaType;
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

import static it.Gruppo1.NowPuglia.config.ApiConfig.ApiBuilder.buildResponse;

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

    @PostMapping(value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> register(@RequestBody @Validated UtentiRegisterDto utente) {
        if (iUtentiRepository.existsByEmail(utente.getEmail())) {
            return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Email già in uso");
        }
        iUsersManagerService.userRegistration(utente);
        return buildResponse(HttpStatus.CREATED, "Utente registrato correttamente");
    }

    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> login(@RequestBody @Validated UtentiLoginDto utenteDto, HttpServletRequest request, HttpServletResponse response) {
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
            return buildResponse(HttpStatus.OK, "Utente loggato correttamente");
        } else {
            return buildResponse(HttpStatus.NOT_FOUND, "Nessun utente trovato, email o password errati");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request) {
        SecurityContext context = SecurityContextHolder.getContext();
        request.getSession().invalidate();
        context.setAuthentication(null);
        SecurityContextHolder.clearContext();
        return buildResponse(HttpStatus.OK, "Logout avvenuto correttamente");
    }

    @PostMapping("/passwordReset")
    public ResponseEntity<ApiResponse<String>> changePassword(@RequestBody @Validated PasswordResetDto passwordResetDto) {
        String currentPassword = passwordResetDto.getCurrentPassword();
        String newPassword = passwordResetDto.getNewPassword();
        String confirmPassword = passwordResetDto.getConfirmPassword();

        if (currentPassword == null || newPassword == null || confirmPassword == null) {
            return buildResponse(HttpStatus.BAD_REQUEST, "Le password non possono essere nulle");
        }

        UtentiModel utentiModel = iUtentiRepository.findByEmail(iUsersManagerService.getEmailFromToken());

        if (utentiModel == null) {
            return buildResponse(HttpStatus.NOT_FOUND, "Utente non trovato");
        }

        if (!passwordEncoder.matches(currentPassword, utentiModel.getPassword())) {
            return buildResponse(HttpStatus.NOT_FOUND, "Nessun utente trovato in questa sessione");
        }

        if (!newPassword.equals(confirmPassword)) {
            return buildResponse(HttpStatus.BAD_REQUEST, "Le password non coincidono");
        }

        iUsersManagerService.passwordReset(iUsersManagerService.getEmailFromToken(), newPassword, passwordEncoder);

        return buildResponse(HttpStatus.OK, "Password cambiata correttamente");
    }

    @PostMapping("/changePlan")
    public ResponseEntity<ApiResponse<String>> changePlan(@RequestBody @Validated ChangePlanDto changePlanDto) {
        UtentiModel utentiModel = iUtentiRepository.findByEmail(iUsersManagerService.getEmailFromToken());
        if (utentiModel != null) {
            if (iAbbonamentiRepository.existsById(changePlanDto.getPlanNumber())) {
                utentiModel.setAbbonamentoInfo(iAbbonamentiRepository.findById(changePlanDto.getPlanNumber()));
                iUtentiRepository.save(utentiModel);
                return buildResponse(HttpStatus.OK, "Piano cambiato con successo");
            } else {
                return buildResponse(HttpStatus.UNAUTHORIZED, "Abbonamento non valido");
            }
        } else {
            return buildResponse(HttpStatus.UNAUTHORIZED, "Utente non valido");
        }
    }
}
