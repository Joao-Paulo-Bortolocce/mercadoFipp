package unoeste.fipp.mercadofipp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unoeste.fipp.mercadofipp.entities.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    public Usuario findByNome(String nome);
}
