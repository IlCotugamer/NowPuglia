package it.Gruppo1.NowPuglia.serviceImp;

import it.Gruppo1.NowPuglia.dto.UtentiRegisterDto;
import it.Gruppo1.NowPuglia.model.UtentiModel;
import it.Gruppo1.NowPuglia.repository.IAbbonamentiRepository;
import it.Gruppo1.NowPuglia.repository.IUtentiRepository;
import it.Gruppo1.NowPuglia.service.IUsersManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersManagerService implements IUsersManagerService {
    private final IAbbonamentiRepository iAbbonamentiRepository;
    private final IUtentiRepository iUtentiRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersManagerService(PasswordEncoder passwordEncoder, IAbbonamentiRepository iAbbonamentiRepository, IUtentiRepository iUtentiRepository) {
        this.passwordEncoder = passwordEncoder;
        this.iAbbonamentiRepository = iAbbonamentiRepository;
        this.iUtentiRepository = iUtentiRepository;
    }

    @Override
    public void userRegistration(UtentiRegisterDto utentiRegisterDto) {
        UtentiModel utentiModel = new UtentiModel(
                utentiRegisterDto.getNome(),
                utentiRegisterDto.getCognome(),
                utentiRegisterDto.getDataNascita(),
                0, //0 = Utente Classico, 1 = Studente o Ricercatore (MAX WEB USAGE AND 1 DEVICE 100 CALL API FOR HOUR, 2 = Azienda (MAX API USAGE)
                utentiRegisterDto.getUsername(),
                passwordEncoder.encode(utentiRegisterDto.getPassword()),
                iAbbonamentiRepository.findById(utentiRegisterDto.getAbbonamentoInfo())
        );

        iUtentiRepository.save(utentiModel);
    }
}
