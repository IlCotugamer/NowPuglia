package it.Gruppo1.NowPuglia.config;

import it.Gruppo1.NowPuglia.model.AbbonamentiModel;
import it.Gruppo1.NowPuglia.repository.IAbbonamentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitDbConfig {
    private final IAbbonamentiRepository abbonamentiRepository;

    @Autowired
    public InitDbConfig(IAbbonamentiRepository abbonamentiRepository) {
        this.abbonamentiRepository = abbonamentiRepository;
    }

    @Bean
    public CommandLineRunner initDb() {
        return args -> {
            abbonamentiRepository.save(
                    new AbbonamentiModel(
                            "USER"
                    )
            );

            abbonamentiRepository.save(
                    new AbbonamentiModel(
                            "BASIC"
                    )
            );
            abbonamentiRepository.save(
                    new AbbonamentiModel(
                            "MEMBER"
                    )
            );

            abbonamentiRepository.save(
                    new AbbonamentiModel(
                            "COMPANY"
                    )
            );
        };
    }
}
