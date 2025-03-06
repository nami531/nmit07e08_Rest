package examen.nmit07e08.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import examen.nmit07e08.domain.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, Long>{
    
}
