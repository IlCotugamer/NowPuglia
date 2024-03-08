package it.Gruppo1.EcoPuglia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "energia")
public class EnergiaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String provincia;

    private String comune;

    private String fonte;

    private float potenza;
}

