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
    @Column(name = "dataNascita")
    private LocalDate dataNascita;
    @Column(name = "tipoUtente", length = 1)
    private int tipoUtente;
    @NotNull
    @NotEmpty(message = "Il campo username non può essere vuoto")
    @Column(name = "email", length = 60, unique = true)
    private String email;
    @NotNull
    @NotEmpty(message = "Il campo password non può essere vuoto")
    @Column(name = "password", length = 60)
    private String password;
    @ManyToOne
    @JoinColumn(name = "CODAbbonamento", referencedColumnName = "IDAbbonamento")
    private AbbonamentiModel abbonamentoInfo;

    public UtentiModel(String nome, String cognome, LocalDate dataNascita, int tipoUtente, String email,String encode, AbbonamentiModel abbonamentoInfo) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.tipoUtente = tipoUtente;
        this.email = email;
        this.password = encode;
        this.abbonamentoInfo = abbonamentoInfo;
    }
}