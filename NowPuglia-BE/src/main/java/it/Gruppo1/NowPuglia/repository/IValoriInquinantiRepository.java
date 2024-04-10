package it.Gruppo1.NowPuglia.repository;

import it.Gruppo1.NowPuglia.model.SensoriModel;
import it.Gruppo1.NowPuglia.model.ValoriInquinantiModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IValoriInquinantiRepository extends JpaRepository<ValoriInquinantiModel, Integer> {
    boolean existsByTipoValoreAndValoreAndSensoreInfo(@NotNull @NotEmpty(message = "Il campo tipoValore non può essere vuoto") String tipoValore, @NotNull @NotEmpty(message = "Il campo valore non può essere vuoto") String valore, SensoriModel sensoreInfo);
    ValoriInquinantiModel findByTipoValoreAndValoreAndSensoreInfo(@NotNull @NotEmpty(message = "Il campo tipoValore non può essere vuoto") String tipoValore, @NotNull @NotEmpty(message = "Il campo valore non può essere vuoto") String valore, SensoriModel sensoreInfo);
}
