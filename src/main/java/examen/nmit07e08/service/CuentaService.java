package examen.nmit07e08.service;

import java.util.List;

import examen.nmit07e08.domain.Cuenta;
import examen.nmit07e08.domain.dto.CuentaDTO;
import examen.nmit07e08.exception.CuentaNotFound;

public interface CuentaService {
    List<Cuenta> obtenerCuentas(); 
    Cuenta obtenerCuenta(long id) throws CuentaNotFound; 
    Cuenta crearCuenta(CuentaDTO cuenta); 
    Cuenta editarCuenta(long id, Cuenta cuenta)  throws CuentaNotFound; 
    void eliminarCuenta(long id) throws CuentaNotFound; 
}
