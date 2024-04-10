package it.Gruppo1.NowPuglia.repository;

import it.Gruppo1.NowPuglia.model.CittaModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICittaRepository extends JpaRepository<CittaModel, Integer> {
    boolean existsByNomeCitta(@NotEmpty(message = "Il nome della città non può essere vuoto") String nomeCitta);
    CittaModel findByNomeCitta(@NotNull @NotEmpty(message = "Il nome della città non può essere vuoto") String nomeCitta);
}
