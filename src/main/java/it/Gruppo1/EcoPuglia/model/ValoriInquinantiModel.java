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
@Table(name  = "ValoriInquinanti")
public class ValoriInquinantiModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Il campo tipoValore non può essere vuoto")
    private String tipoValore;
    @NotEmpty(message = "Il campo valore non può essere vuoto")
    private String valore;
    @ManyToOne
    @JoinColumn(name = "CODAria", referencedColumnName = "IDAria")
    private AriaModel ariaModel;
}