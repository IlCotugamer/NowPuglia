package it.Gruppo1.NowPuglia.repository;

import it.Gruppo1.NowPuglia.model.UtentiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUtentiRepository extends JpaRepository<UtentiModel, Integer> {
}
