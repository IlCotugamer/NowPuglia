package it.Gruppo1.NowPuglia.repository;

import it.Gruppo1.NowPuglia.model.AbbonamentiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAbbonamentiRepository extends JpaRepository<AbbonamentiModel, Integer> {
}
