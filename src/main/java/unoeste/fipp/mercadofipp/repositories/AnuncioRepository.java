package unoeste.fipp.mercadofipp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unoeste.fipp.mercadofipp.entities.Anuncio;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {
}
