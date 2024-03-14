package it.Gruppo1.EcoPuglia.model;

import com.google.gson.annotations.SerializedName;
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
    @SerializedName("_id")
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @SerializedName("data_di_misurazione")
    private LocalDateTime dataDiMisurazione;
    @SerializedName("id_station")
    private Integer idStation;
    private String denominazione;
    private String comune;
    private String provincia;
    private double longitude;
    private double latitude;
    @SerializedName("tipologia_di_area")
    private String tipologiaDiArea;
    @SerializedName("tipologia_di_stazione")
    private String tipologiaDiStazione;
    private String rete;
    @SerializedName("interesse_rete")
    private String interesseRete;
    @SerializedName("inquinante_misurato")
    private String inquinanteMisurato;
    @SerializedName("valore_inquinante_misurato")
    private float valoreInquinanteMisurato;
    private Integer limite;
    @SerializedName("unita_misura")
    private String unitaMisura;
    private Integer superamenti;
    @SerializedName("indice_qualita")
    private Integer indiceQualita;
    @SerializedName("classe_qualita")
    private String classeQualita;
}
