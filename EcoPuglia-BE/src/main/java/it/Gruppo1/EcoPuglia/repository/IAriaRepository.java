package it.Gruppo1.EcoPuglia.repository;

import it.Gruppo1.EcoPuglia.model.AriaModel;
import it.Gruppo1.EcoPuglia.model.CittaModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface IAriaRepository extends JpaRepository<AriaModel, Integer> {
    boolean existsByTipoAreaAndDataMisurazioneAndCittaInfo(@NotEmpty(message = "L'area non può essere vuota") int tipoArea, @NotEmpty(message = "La data non può essere vuota") LocalDateTime dataMisurazione, CittaModel cittaInfo);
    AriaModel findByTipoAreaAndDataMisurazioneAndCittaInfo(@NotNull(message = "L'area non può essere vuota") int tipoArea, @NotNull(message = "La data non può essere vuota") LocalDateTime dataMisurazione, CittaModel cittaInfo);
}
