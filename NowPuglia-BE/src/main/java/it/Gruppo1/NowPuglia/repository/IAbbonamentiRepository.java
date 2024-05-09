package it.Gruppo1.NowPuglia.repository;

import it.Gruppo1.NowPuglia.model.AbbonamentiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

@Repository
public interface IAbbonamentiRepository extends JpaRepository<AbbonamentiModel, Integer> {
    boolean existsById(@Validated int id);
    AbbonamentiModel findById(@Validated int id);
}
