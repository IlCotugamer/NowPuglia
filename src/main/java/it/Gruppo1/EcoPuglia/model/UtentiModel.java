package it.Gruppo1.EcoPuglia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Il campo nome non può essere vuoto")
    private String nome;
    @NotEmpty(message = "Il campo cognome non può essere vuoto")
    private String cognome;
    @NotEmpty(message = "Il campo dataNascita non può essere vuoto")
    private LocalDateTime dataNascita;
    @NotEmpty(message = "Il campo tipoUtente non può essere vuoto")
    private int tipoUtente;
    @NotEmpty(message = "Il campo username non può essere vuoto")
    private String username;
    @NotEmpty(message = "Il campo password non può essere vuoto")
    private String password;
}