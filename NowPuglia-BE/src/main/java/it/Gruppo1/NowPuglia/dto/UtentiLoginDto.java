package it.Gruppo1.NowPuglia.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UtentiLoginDto {
    @NotNull(message = "la email non può essere nulla")
    @NotEmpty(message = "la email non può essere vuota")
    private String email;
    @NotNull(message = "la password non può essere nulla")
    @NotEmpty(message = "la password non può essere vuota")
    private String password;
}
