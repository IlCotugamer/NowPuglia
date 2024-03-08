package it.Gruppo1.EcoPuglia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "aria")
public class AriaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataDiMisurazione;

    private Integer idStation;
    private String denominazione;
    private String comune;
    private String provincia;
    private String longitude;
    private String latitude;
    private String tipologiaDiArea;
    private String tipologiaDiStazione;
    private String rete;
    private String interesseRete;
    private String inquinanteMisurato;
    private Integer valoreInquinanteMisurato;
    private Integer limite;
    private String unitaMisura;
    private Integer superamenti;
    private Integer indiceQualita;
    private String classeQualita;
}

