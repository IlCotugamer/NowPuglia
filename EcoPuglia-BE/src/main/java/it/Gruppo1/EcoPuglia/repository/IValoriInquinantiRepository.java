package it.Gruppo1.EcoPuglia.repository;

import it.Gruppo1.EcoPuglia.model.AriaModel;
import it.Gruppo1.EcoPuglia.model.ValoriInquinantiModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IValoriInquinantiRepository extends JpaRepository<ValoriInquinantiModel, Integer> {
    boolean existsByTipoValoreAndValoreAndAriaInfo(@NotNull @NotEmpty(message = "Il campo tipoValore non può essere vuoto") String tipoValore, @NotNull @NotEmpty(message = "Il campo valore non può essere vuoto") String valore, AriaModel ariaInfo);
    ValoriInquinantiModel findByTipoValoreAndValoreAndAriaInfo(@NotNull @NotEmpty(message = "Il campo tipoValore non può essere vuoto") String tipoValore, @NotNull @NotEmpty(message = "Il campo valore non può essere vuoto") String valore, AriaModel ariaInfo);
}
