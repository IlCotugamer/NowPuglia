package it.Gruppo1.NowPuglia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name  = "Citta")
public class CittaModel {
    @Id
    @Column(name = "IDCitta", length = 4)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nomeCitta", length = 50)
    @NotNull
    @NotEmpty(message = "Il nome della città non può essere vuoto")
    private String nomeCitta;

    public CittaModel(String nomeCitta) {
        this.nomeCitta = nomeCitta;
    }
}