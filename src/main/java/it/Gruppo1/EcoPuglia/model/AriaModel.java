package it.Gruppo1.EcoPuglia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @Column(name = "IDAria")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "L'area non può essere vuota")
    private int tipoArea;
    @NotNull
    @NotEmpty(message = "La data non può essere vuota")
    private LocalDateTime dataMisurazione;
    @ManyToOne
    @JoinColumn(name = "CODCitta", referencedColumnName = "IDCitta")
    private CittaModel cittaInfo;

    public AriaModel(int tipoArea, LocalDateTime dataMisurazione, CittaModel cittaInfo) {
        this.tipoArea = tipoArea;
        this.dataMisurazione = dataMisurazione;
        this.cittaInfo = cittaInfo;
    }
}