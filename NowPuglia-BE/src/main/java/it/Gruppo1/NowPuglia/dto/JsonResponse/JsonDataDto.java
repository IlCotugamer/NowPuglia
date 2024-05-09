package it.Gruppo1.NowPuglia.dto.JsonResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JsonDataDto {
    private long misurazioniTotali;
    private LinkedList<MisurazioniDto> misurazioni;
}
