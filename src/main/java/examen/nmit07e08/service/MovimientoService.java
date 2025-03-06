package examen.nmit07e08.service;

import java.util.List;

import examen.nmit07e08.domain.Movimiento;
import examen.nmit07e08.domain.dto.MovimientoDTO;
import examen.nmit07e08.exception.CuentaNotFound;
import examen.nmit07e08.exception.MovimientoNotFound;

public interface MovimientoService {
    List<Movimiento> obtenerMovimientos(); 
    List<Movimiento> obtenerMovimientosCuenta(long idCuenta) throws CuentaNotFound; 

    Movimiento obtenerMovimiento(long id) throws MovimientoNotFound; 

    Movimiento crearMovimiento(MovimientoDTO movDTO); 
    Movimiento editarMovimiento(long id, MovimientoDTO movDTO) throws MovimientoNotFound; 
    void eliminarMovimiento(long id) throws MovimientoNotFound; 

}
