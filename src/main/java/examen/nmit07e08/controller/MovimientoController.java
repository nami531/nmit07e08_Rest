package examen.nmit07e08.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import examen.nmit07e08.domain.dto.MovimientoDTO;
import examen.nmit07e08.service.MovimientoService;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    
    @Autowired
    private MovimientoService service; 


    @GetMapping("/")
    public ResponseEntity<?> obtenerMovimientos() {
        return ResponseEntity.ok(service.obtenerMovimientos()); 
    }

    @GetMapping("/{cuentaId}")
    public ResponseEntity<?> obtenerMovimientosCuenta(@PathVariable long cuentaId) {
        return ResponseEntity.ok(service.obtenerMovimientosCuenta(cuentaId)); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerMovimiento(@PathVariable long id) {
        return ResponseEntity.ok(service.obtenerMovimiento(id)); 
    }

    @PostMapping("/")
    public ResponseEntity<?> crearMovimiento(@RequestBody MovimientoDTO movDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearMovimiento(movDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarMovimiento(@PathVariable long id, @RequestBody MovimientoDTO movDTO) {
        return ResponseEntity.ok(service.editarMovimiento(id, movDTO));
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarMovimiento(@PathVariable long id){
        service.eliminarMovimiento(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
    }
}
