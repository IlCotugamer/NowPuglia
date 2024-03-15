package it.Gruppo1.EcoPuglia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name  = "Citta")
public class CittaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Il nome della città non può essere vuoto")
    private String nomeCitta;
    @NotEmpty(message = "La longitudine non può essere vuota")
    private String longitude;
    @NotEmpty(message = "La latitudine non può essere vuota")
    private String latitude;
}