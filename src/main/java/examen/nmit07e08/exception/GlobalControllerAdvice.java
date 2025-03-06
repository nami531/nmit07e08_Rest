package examen.nmit07e08.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(CuentaNotFound.class)
    public ResponseEntity<?> handleCuentaNotFound(CuentaNotFound ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()); 
    }

    @ExceptionHandler(MovimientoNotFound.class)
    public ResponseEntity<?> handleMovimientoNotFound(MovimientoNotFound ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()); 
    }
    
    @ExceptionHandler(UsuarioNotFound.class)
    public ResponseEntity<?> handleUsuarioNotFound(UsuarioNotFound ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()); 
    }
    
    @ExceptionHandler(NotTitularCuenta.class)
    public ResponseEntity<?> handleNotTitularCuenta(NotTitularCuenta ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage()); 
    }
    
    @ExceptionHandler(UnauthorizedMovimiento.class)
    public ResponseEntity<?> handleUnauthorizedMovimiento(UnauthorizedMovimiento ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage()); 
    }
}
