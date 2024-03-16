package it.Gruppo1.EcoPuglia.repository;

import it.Gruppo1.EcoPuglia.model.CittaModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICittaRepository extends JpaRepository<CittaModel, Integer> {
    boolean existsByLongitudeAndLatitude(@NotNull @NotEmpty(message = "La longitudine non può essere vuota") String longitude, @NotNull @NotEmpty(message = "La latitudine non può essere vuota") String latitude);
    boolean existsByNomeCittaAndLongitudeAndLatitude(@NotEmpty(message = "Il nome della città non può essere vuoto") String nomeCitta, @NotEmpty(message = "La longitudine non può essere vuota") String longitude, @NotEmpty(message = "La latitudine non può essere vuota") String latitude);
    CittaModel findByLongitudeAndLatitude(@NotNull @NotEmpty(message = "La longitudine non può essere vuota") String longitude, @NotNull @NotEmpty(message = "La latitudine non può essere vuota") String latitude);
}
