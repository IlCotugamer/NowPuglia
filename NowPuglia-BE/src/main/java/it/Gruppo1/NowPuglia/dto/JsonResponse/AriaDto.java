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
public class AriaDto {
    private String tipoValoreInquinante;
    private String valoreInquinante;
    private String latitudine;
    private String longitudine;
}
