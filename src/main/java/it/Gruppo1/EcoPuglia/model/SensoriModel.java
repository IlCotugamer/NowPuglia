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
@Table(name  = "Sensori")
public class SensoriModel {
    @Id
    @Column(name = "IDSensore")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @NotEmpty(message = "La longitudine non può essere vuota")
    private String longitude;
    @NotNull
    @NotEmpty(message = "La latitudine non può essere vuota")
    private String latitude;
    @ManyToOne
    @JoinColumn(name = "CODAria", referencedColumnName = "IDAria")
    private AriaModel ariaInfo;

    public SensoriModel(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}