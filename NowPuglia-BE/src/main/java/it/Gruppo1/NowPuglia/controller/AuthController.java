package it.Gruppo1.NowPuglia.controller;

import it.Gruppo1.NowPuglia.config.ApiConfig.ApiError;
import it.Gruppo1.NowPuglia.config.ApiConfig.ApiResponse;
import it.Gruppo1.NowPuglia.config.ApiConfig.ErrorDetail;
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
import java.util.Collections;
import java.util.Date;

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
    public ResponseEntity<ApiResponse<UtentiRegisterDto>> register(@RequestBody @Validated UtentiRegisterDto utente) {
        if (iUtentiRepository.existsByEmail(utente.getEmail()))
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.<UtentiRegisterDto>builder()
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .statusMessage("Email già in uso")
                            .returnedObjects(0)
                            .totalObjects(0)
                            .errors(
                                    ApiError.builder()
                                            .path("/api/auth/register")
                                            .timestamp(new Date())
                                            .details(
                                                    Collections.singletonList(
                                                            ErrorDetail.builder()
                                                                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                                                                    .field("email")
                                                                    .source("iUtentiRepository.existsByEmail(utente.getEmail())")
                                                                    .message("Email già in uso").build()
                                                    )
                                            ).build()
                            ).build()
            );
        iUsersManagerService.userRegistration(utente);
        utente.setPassword("hidden");
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<UtentiRegisterDto>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .statusMessage("User registrato correttamente")
                        .returnedObjects(1)
                        .totalObjects(1)
                        .payload(utente).build()
        );
    }

    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> login(@RequestBody @Validated UtentiLoginDto utenteDto, HttpServletRequest request, HttpServletResponse response) {
        ApiResponse.ApiResponseBuilder<String>builder = ApiResponse.builder();
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

            builder
                    .statusCode(HttpStatus.OK.value())
                    .statusMessage("User loggato correttamente")
                    .returnedObjects(1)
                    .totalObjects(1)
                    .build();
        } else {
            builder
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .statusMessage("Nessun utente trovato, email o password errati")
                    .returnedObjects(0)
                    .totalObjects(0).build();
        }
        ApiResponse<String> risposta = builder.build();
        return ResponseEntity.status(HttpStatus.OK).body(risposta);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request) {
        ApiResponse.ApiResponseBuilder<String>builder = ApiResponse.builder();
        SecurityContext context = SecurityContextHolder.getContext();
        request.getSession().invalidate();
        context.setAuthentication(null);
        SecurityContextHolder.clearContext();
        builder
                .statusCode(HttpStatus.OK.value())
                .statusMessage("Logout avvenuto correttamente")
                .returnedObjects(0)
                .totalObjects(0).build();

        ApiResponse<String> risposta = builder.build();
        return ResponseEntity.status(HttpStatus.OK).body(risposta);
    }

    @PostMapping("/passwordReset")
    public ResponseEntity<ApiResponse<String>> changePassword(@RequestBody @Validated PasswordResetDto passwordResetDto) {
        ApiResponse.ApiResponseBuilder<String>builder = ApiResponse.builder();
        String currentPassword = passwordResetDto.getCurrentPassword();
        String newPassword = passwordResetDto.getNewPassword();
        String confirmPassword = passwordResetDto.getConfirmPassword();
        if (currentPassword == null || newPassword == null || confirmPassword == null) {
            builder
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .statusMessage("Le password non possono essere nulle")
                    .returnedObjects(0)
                    .totalObjects(0).build();
            ApiResponse<String> risposta = builder.build();
            return ResponseEntity.status(HttpStatus.OK).body(risposta);
        }
        UtentiModel utentiModel = iUtentiRepository.findByEmail(iUsersManagerService.getEmailFromToken());
        if (utentiModel != null) {
            if (passwordEncoder.matches(currentPassword, utentiModel.getPassword())) {
                if (newPassword.equals(confirmPassword)) {
                    iUsersManagerService.passwordReset(
                            iUsersManagerService.getEmailFromToken(),
                            newPassword,
                            passwordEncoder
                    );
                } else {
                    builder
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .statusMessage("Le password non coincidono")
                            .returnedObjects(0)
                            .totalObjects(0).build();
                    ApiResponse<String> risposta = builder.build();
                    return ResponseEntity.status(HttpStatus.OK).body(risposta);
                }
            } else {
                builder
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .statusMessage("Nessun utente trovato in questa sessione")
                        .returnedObjects(0)
                        .totalObjects(0).build();
                ApiResponse<String> risposta = builder.build();
                return ResponseEntity.status(HttpStatus.OK).body(risposta);
            }
        } else {
            builder
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .statusMessage("Utente non trovato")
                    .returnedObjects(0)
                    .totalObjects(0).build();
            ApiResponse<String> risposta = builder.build();
            return ResponseEntity.status(HttpStatus.OK).body(risposta);
        }
        builder
                .statusCode(HttpStatus.OK.value())
                .statusMessage("Password cambiata correttamente")
                .returnedObjects(1)
                .totalObjects(1).build();
        ApiResponse<String> risposta = builder.build();
        return ResponseEntity.status(HttpStatus.OK).body(risposta);
    }

    @PostMapping("/changePlan")
    public ResponseEntity<String> changePlan(@RequestBody @Validated ChangePlanDto changePlanDto) {
        UtentiModel utentiModel = iUtentiRepository.findByEmail(iUsersManagerService.getEmailFromToken());
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
