package examen.nmit07e08.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import examen.nmit07e08.domain.Cuenta;
import examen.nmit07e08.domain.Movimiento;


public interface MovimientoRepository extends JpaRepository<Movimiento, Long>{
    List<Movimiento> findByCuenta(Cuenta cuenta);
}
