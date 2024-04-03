package it.Gruppo1.EcoPuglia.repository;

import it.Gruppo1.EcoPuglia.model.CittaModel;
import it.Gruppo1.EcoPuglia.model.SensoriModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISensoriRepository extends JpaRepository<SensoriModel, Integer> {
    boolean existsByLongitudeAndLatitudeAndCittaInfo(@NotNull @NotEmpty(message = "La longitudine non può essere vuota") String longitude, @NotNull @NotEmpty(message = "La latitudine non può essere vuota") String latitude, CittaModel cittaInfo);
    SensoriModel findByLongitudeAndLatitudeAndCittaInfo(@NotNull @NotEmpty(message = "La longitudine non può essere vuota") String longitude, @NotNull @NotEmpty(message = "La latitudine non può essere vuota") String latitude, CittaModel cittaInfo);
}

