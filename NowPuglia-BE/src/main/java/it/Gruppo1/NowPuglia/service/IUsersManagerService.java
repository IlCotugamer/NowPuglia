package it.Gruppo1.NowPuglia.service;

import it.Gruppo1.NowPuglia.dto.UtentiRegisterDto;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface IUsersManagerService{
    void userRegistration(UtentiRegisterDto utentiRegisterDto);
    void passwordReset(String username, String password, PasswordEncoder passwordEncoder);
    String getUsernameFromToken();
}
