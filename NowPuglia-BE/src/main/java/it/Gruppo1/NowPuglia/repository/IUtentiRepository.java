package it.Gruppo1.NowPuglia.repository;

import it.Gruppo1.NowPuglia.model.UtentiModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUtentiRepository extends JpaRepository<UtentiModel, Integer> {
 boolean findByUsername(@NotNull @NotEmpty(message = "Il campo username non può essere vuoto") String username);
}
