package it.Gruppo1.NowPuglia.dto;

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
public class AriaDto {
    private String latitudine;
    private String longitudine;
    private String tipoValoreInquinante;
    private String valoreInquinante;
}
