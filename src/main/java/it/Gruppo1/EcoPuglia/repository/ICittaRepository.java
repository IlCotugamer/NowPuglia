package it.Gruppo1.EcoPuglia.repository;

import it.Gruppo1.EcoPuglia.model.CittaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICittaRepository extends JpaRepository<CittaModel, Integer> {
}
