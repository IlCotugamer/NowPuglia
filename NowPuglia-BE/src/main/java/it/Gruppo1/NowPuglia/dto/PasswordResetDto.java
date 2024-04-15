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
public class PasswordResetDto {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
