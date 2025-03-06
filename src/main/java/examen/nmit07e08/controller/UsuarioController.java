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

import examen.nmit07e08.domain.Usuario;
import examen.nmit07e08.service.UsuarioService;




@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService service; 

    @GetMapping("/")
    public ResponseEntity<?> obtenerUsuarios() {
        return ResponseEntity.ok(service.obtenerUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable long id) {
        return ResponseEntity.ok(service.obtenerUsuario(id));
    }

    @PostMapping("/")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearUsuario(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarUsuario(@PathVariable long id, @RequestBody Usuario usuario) {
                
        return ResponseEntity.ok(service.editarUsuario(id, usuario));
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable long id){
        service.eliminarUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
    }
    
}
