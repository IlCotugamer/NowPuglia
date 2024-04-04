package it.Gruppo1.EcoPuglia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @Column(name = "IDValoreInquinante", length = 3)
    private int id;
    @NotNull
    @NotEmpty(message = "Il campo tipoValore non può essere vuoto")
    @Column(name = "tipoValore", length = 10)
    private String tipoValore;
    @NotNull
    @NotEmpty(message = "Il campo valore non può essere vuoto")
    @Column(name = "valore", length = 10)
    private String valore;
    @ManyToOne
    @JoinColumn(name = "CODSensore", referencedColumnName = "IDSensore")
    private SensoriModel sensoreInfo;

    public ValoriInquinantiModel(String tipoValore, String valore, SensoriModel sensoreInfo) {
        this.tipoValore = tipoValore;
        this.valore = valore;
        this.sensoreInfo = sensoreInfo;
    }
}