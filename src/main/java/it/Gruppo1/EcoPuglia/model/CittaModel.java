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
    @NotNull
    @NotEmpty(message = "La longitudine non può essere vuota")
    private String longitude;
    @NotNull
    @NotEmpty(message = "La latitudine non può essere vuota")
    private String latitude;

    public CittaModel(String nomeCitta, String longitude, String latitude) {
        this.nomeCitta = nomeCitta;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}