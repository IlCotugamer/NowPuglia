package it.Gruppo1.EcoPuglia.repository;

import it.Gruppo1.EcoPuglia.model.AriaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAriaRepository extends JpaRepository<AriaModel, Integer> {
}
