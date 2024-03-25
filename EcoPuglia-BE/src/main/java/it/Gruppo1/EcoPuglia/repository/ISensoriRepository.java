package it.Gruppo1.EcoPuglia.repository;

import it.Gruppo1.EcoPuglia.model.AriaModel;
import it.Gruppo1.EcoPuglia.model.SensoriModel;
import it.Gruppo1.EcoPuglia.model.ValoriInquinantiModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISensoriRepository extends JpaRepository<SensoriModel, Integer> {
    boolean existsByLongitudeAndLatitudeAndAriaInfo(@NotNull @NotEmpty(message = "La longitudine non può essere vuota") String longitude, @NotNull @NotEmpty(message = "La latitudine non può essere vuota") String latitude, AriaModel ariaInfo);
    SensoriModel findByLongitudeAndLatitudeAndAriaInfo(@NotNull @NotEmpty(message = "La longitudine non può essere vuota") String longitude, @NotNull @NotEmpty(message = "La latitudine non può essere vuota") String latitude, AriaModel ariaInfo);
}

