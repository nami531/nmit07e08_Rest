package examen.nmit07e08.exception;

public class UsuarioNotFound extends RuntimeException{
    public UsuarioNotFound(long id){
        super("Usuario con id: " + id + " no encontrado"); 
    }
}
