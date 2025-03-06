package examen.nmit07e08.exception;

public class MovimientoNotFound extends RuntimeException {
    public MovimientoNotFound(long id){
        super("Movimiento con id: " + id + " no encontrado"); 
    }
}
