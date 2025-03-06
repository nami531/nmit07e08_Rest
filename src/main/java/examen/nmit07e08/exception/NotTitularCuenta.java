package examen.nmit07e08.exception;

public class NotTitularCuenta extends RuntimeException {
    public NotTitularCuenta(){
        super("No eres el titular de esta cuenta, así que no puedes modificarla"); 
    }
}
