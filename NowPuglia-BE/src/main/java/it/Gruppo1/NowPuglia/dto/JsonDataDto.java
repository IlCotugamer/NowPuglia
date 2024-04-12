package it.Gruppo1.NowPuglia.dto;

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
    private LinkedList<MisurazioniDto> misurazioni;
    private int misurazioniTotali;
}
