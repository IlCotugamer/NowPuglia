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
@Table(name  = "Energia")
public class EnergiaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Il campo fonte non può essere vuoto")
    private int fonte;
    @NotEmpty(message = "Il campo potenza non può essere vuoto")
    private String potenza;
    @ManyToOne
    @JoinColumn(name = "CODCitta", referencedColumnName = "IDCitta")
    private CittaModel cittaModel;
}