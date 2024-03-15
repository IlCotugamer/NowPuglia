package it.Gruppo1.EcoPuglia.repository;

import it.Gruppo1.EcoPuglia.model.ValoriInquinantiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IValoriInquinantiRepository extends JpaRepository<ValoriInquinantiModel, Integer> {
}
