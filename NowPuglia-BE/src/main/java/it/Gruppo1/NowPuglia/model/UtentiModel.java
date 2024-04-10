package it.Gruppo1.NowPuglia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
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
    @Column(name = "IDUtente", length = 6)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @NotEmpty(message = "Il campo nome non può essere vuoto")
    @Column(name = "nome", length = 30)
    private String nome;
    @NotNull
    @NotEmpty(message = "Il campo cognome non può essere vuoto")
    @Column(name = "cognome", length = 15)
    private String cognome;
    @NotNull
    @NotEmpty(message = "Il campo dataNascita non può essere vuoto")
    @Column(name = "dataNascita")
    private LocalDateTime dataNascita;
    @NotNull
    @NotEmpty(message = "Il campo tipoUtente non può essere vuoto")
    @Column(name = "tipoUtente", length = 1)
    private int tipoUtente;
    @NotNull
    @NotEmpty(message = "Il campo username non può essere vuoto")
    @Column(name = "username", length = 20)
    private String username;
    @NotNull
    @NotEmpty(message = "Il campo password non può essere vuoto")
    @Column(name = "password")
    private String password;
    @ManyToOne
    @JoinColumn(name = "CODAbbonamento", referencedColumnName = "IDAbbonamento")
    private AbbonamentiModel abbonamentoInfo;

    public UtentiModel(String nome, String cognome, LocalDate dataNascita, int tipoUtente, String username,String encode) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita.atStartOfDay();
        this.tipoUtente = tipoUtente;
        this.username = username;
        this.password = encode;
    }
}