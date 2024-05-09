package it.Gruppo1.NowPuglia.dto.JsonResponse;

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
public class MisurazioniDto {
    private String citta;
    private EnergiaDto energiaRinnovabileProdotta;
    private AriaDto valoriAriaInquinata;
}
