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
@Table(name  = "Sensori")
public class SensoriModel {
    @Id
    @Column(name = "IDSensore", length = 3)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @NotEmpty(message = "La longitudine non può essere vuota")
    @Column(name = "longitudine", length = 10)
    private String longitude;
    @NotNull
    @NotEmpty(message = "La latitudine non può essere vuota")
    @Column(name = "latitudine", length = 10)
    private String latitude;
    @ManyToOne
    @JoinColumn(name = "CODCitta", referencedColumnName = "IDCitta")
    private CittaModel cittaInfo;

    public SensoriModel(String longitude, String latitude, CittaModel cittaModel) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.cittaInfo = cittaModel;
    }
}