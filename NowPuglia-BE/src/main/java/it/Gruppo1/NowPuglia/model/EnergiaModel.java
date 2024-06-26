package it.Gruppo1.NowPuglia.model;

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
@Table(name  = "Energia")
public class EnergiaModel {
    @Id
    @Column(name = "IDEnergia", length = 3)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "Il campo fonte non può essere vuoto")
    @Column(name = "fonte", length = 1)
    private int fonte;
    @NotNull
    @NotEmpty(message = "Il campo potenza non può essere vuoto")
    @Column(name = "potenza", length = 4)
    private String potenza;
    @ManyToOne
    @JoinColumn(name = "CODCitta", referencedColumnName = "IDCitta")
    private CittaModel cittaInfo;

    public EnergiaModel(int fonte, String potenza, CittaModel cittaInfo) {
        this.fonte = fonte;
        this.potenza = potenza;
        this.cittaInfo = cittaInfo;
    }
}