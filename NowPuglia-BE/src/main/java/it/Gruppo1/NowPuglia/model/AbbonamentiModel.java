package it.Gruppo1.NowPuglia.model;

import jakarta.persistence.*;
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
@Table(name  = "Abbonamenti")
public class AbbonamentiModel {
    @Id
    @Column(name = "IDAbbonamento", length = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "L'area non può essere vuota")
    @Column(name = "tipoAbbonamento", length = 3)
    private String tipoAbbonamento;
}