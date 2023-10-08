package cl.desafio.loginey.repository;

import cl.desafio.loginey.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,String> {

  Optional<Usuario> findByEmail(String s);
}
