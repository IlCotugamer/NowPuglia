package it.Gruppo1.EcoPuglia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name  = "Citta")
public class CittaModel {
    @Id
    @Column(name = "IDCitta")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nomeCitta")
    @NotNull
    @NotEmpty(message = "Il nome della città non può essere vuoto")
    private String nomeCitta;

    public CittaModel(String nomeCitta) {
        this.nomeCitta = nomeCitta;
    }
}