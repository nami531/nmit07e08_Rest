package examen.nmit07e08.exception;

public class CuentaNotFound extends RuntimeException {
    public CuentaNotFound(long id){
        super("Cuenta con id: " + id + " no encontrado"); 
    }
}
