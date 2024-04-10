package it.Gruppo1.NowPuglia.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UtentiDto {
    private int id;
    @NotNull
    @NotEmpty(message = "Il campo nome non può essere vuoto")
    private String nome;
    @NotNull
    @NotEmpty(message = "Il campo cognome non può essere vuoto")
    private String cognome;
    @NotNull
    private LocalDateTime dataNascita;
    @NotNull
    private int tipoUtente;
    @NotNull
    @NotEmpty(message = "Il campo username non può essere vuoto")
    private String username;
}
