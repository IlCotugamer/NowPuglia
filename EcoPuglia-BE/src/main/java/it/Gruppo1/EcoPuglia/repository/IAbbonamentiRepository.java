package it.Gruppo1.EcoPuglia.repository;

import it.Gruppo1.EcoPuglia.model.AbbonamentiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAbbonamentiRepository extends JpaRepository<AbbonamentiModel, Integer> {
}
