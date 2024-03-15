package it.Gruppo1.EcoPuglia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name  = "Aria")
public class AriaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "L'area non può essere vuota")
    private int tipoArea;
    @NotEmpty(message = "La data non può essere vuota")
    private LocalDateTime dataMisurazione;
    @ManyToOne
    @JoinColumn(name = "CODCitta", referencedColumnName = "IDCitta")
    private CittaModel cittaModel;
}