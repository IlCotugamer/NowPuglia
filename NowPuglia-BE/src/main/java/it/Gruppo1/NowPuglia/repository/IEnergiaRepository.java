package it.Gruppo1.NowPuglia.repository;

import it.Gruppo1.NowPuglia.model.CittaModel;
import it.Gruppo1.NowPuglia.model.EnergiaModel;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEnergiaRepository extends JpaRepository<EnergiaModel, Integer> {
    boolean existsByFonteAndPotenzaAndCittaInfo(@NotEmpty(message = "Il campo fonte non può essere vuoto") int fonte, @NotEmpty(message = "Il campo potenza non può essere vuoto") String potenza, CittaModel cittaInfo);
}
