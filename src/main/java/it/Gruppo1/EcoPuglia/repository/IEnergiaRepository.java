package it.Gruppo1.EcoPuglia.repository;

import it.Gruppo1.EcoPuglia.model.EnergiaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEnergiaRepository extends JpaRepository<EnergiaModel, Integer> {
}
