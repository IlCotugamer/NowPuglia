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
public class EnergiaDto {
    private int fonte;
    private String potenza;
}
