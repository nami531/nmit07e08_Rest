package examen.nmit07e08.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import examen.nmit07e08.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByDni(String dni);
    Boolean existsByDni(String dni);
}
