package it.Gruppo1.NowPuglia.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UtentiRegisterDto {
    @NotEmpty(message = "Il campo nome non può essere vuoto")
    private String nome;
    @NotNull
    @NotEmpty(message = "Il campo cognome non può essere vuoto")
    private String cognome;
    @NotNull
    private LocalDate dataNascita;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private int abbonamentoInfo;
}
