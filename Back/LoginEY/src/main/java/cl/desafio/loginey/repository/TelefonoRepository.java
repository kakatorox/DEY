package cl.desafio.loginey.repository;

import cl.desafio.loginey.entity.Telefono;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefonoRepository extends JpaRepository<Telefono,Long> {
}
