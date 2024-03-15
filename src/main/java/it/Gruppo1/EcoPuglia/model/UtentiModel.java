package it.Gruppo1.EcoPuglia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name  = "Utenti")
public class UtentiModel {
    @Id
    @Column(name = "IDUtente")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @NotEmpty(message = "Il campo nome non può essere vuoto")
    private String nome;
    @NotNull
    @NotEmpty(message = "Il campo cognome non può essere vuoto")
    private String cognome;
    @NotNull
    @NotEmpty(message = "Il campo dataNascita non può essere vuoto")
    private LocalDateTime dataNascita;
    @NotNull
    @NotEmpty(message = "Il campo tipoUtente non può essere vuoto")
    private int tipoUtente;
    @NotNull
    @NotEmpty(message = "Il campo username non può essere vuoto")
    private String username;
    @NotNull
    @NotEmpty(message = "Il campo password non può essere vuoto")
    private String password;
}