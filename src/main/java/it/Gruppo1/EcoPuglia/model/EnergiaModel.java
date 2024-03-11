package it.Gruppo1.EcoPuglia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@ToString
@Entity
@Table(name = "energia")
public class EnergiaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String provincia;
    @NotNull
    private String comune;
    @NotNull
    private String fonte;
    private float potenza;
}

