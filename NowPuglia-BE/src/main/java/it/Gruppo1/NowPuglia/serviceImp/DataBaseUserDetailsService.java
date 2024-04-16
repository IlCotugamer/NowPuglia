package it.Gruppo1.NowPuglia.serviceImp;

import it.Gruppo1.NowPuglia.model.UtentiModel;
import it.Gruppo1.NowPuglia.repository.IUtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class DataBaseUserDetailsService implements UserDetailsService {
    private final IUtentiRepository utentiRepository;

    @Autowired
    public DataBaseUserDetailsService(IUtentiRepository utentiRepository) {
        this.utentiRepository = utentiRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UtentiModel utenti = utentiRepository.findByEmail(username);
        if (utenti == null) {
            throw new UsernameNotFoundException("Utente non trovato");
        }

        return new User(utenti.getEmail(), utenti.getPassword(), getAuthorities(utenti));
    }

    public static Collection<? extends GrantedAuthority> getAuthorities(UtentiModel utente) {
        String[] userRoles = new String[]{mapAbbonamentoToRole(utente.getAbbonamentoInfo().getTipoAbbonamento())};
        return Arrays.stream(userRoles)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private static String mapAbbonamentoToRole(String tipoAbbonamento) {
        return switch (tipoAbbonamento) {
            case "USER" -> "ROLE_USER";
            case "BASIC" -> "ROLE_BASIC";
            case "MEMBER" -> "ROLE_MEMBER";
            case "COMPANY" -> "ROLE_COMPANY";
            default -> throw new IllegalArgumentException("Tipo di abbonamento non valido");
        };
    }
}
