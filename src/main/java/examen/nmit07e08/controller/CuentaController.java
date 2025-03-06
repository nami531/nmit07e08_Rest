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

import examen.nmit07e08.domain.Cuenta;
import examen.nmit07e08.domain.dto.CuentaDTO;
import examen.nmit07e08.service.CuentaService;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService service; 

    @GetMapping("/")
    public ResponseEntity<?> obtenerCuentas() {
        return ResponseEntity.ok(service.obtenerCuentas()); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCuenta(@PathVariable long id) {
        return ResponseEntity.ok(service.obtenerCuenta(id)); 
    }

    @PostMapping("/")
    public ResponseEntity<?> crearCuenta(@RequestBody CuentaDTO cuenta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearCuenta(cuenta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarCuenta(@PathVariable long id, @RequestBody Cuenta cuenta) {
        return ResponseEntity.ok(service.editarCuenta(id, cuenta));
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarCuenta(@PathVariable long id){
        service.eliminarCuenta(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
    }
}
