package examen.nmit07e08.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import examen.nmit07e08.domain.Usuario;
import examen.nmit07e08.exception.UsuarioNotFound;
import examen.nmit07e08.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository; 

    @Override
    public List<Usuario> obtenerUsuarios() {
        return repository.findAll(); 
    }

    @Override
    public Usuario obtenerUsuario(long id) throws UsuarioNotFound {
        return repository.findById(id).orElseThrow(() -> new UsuarioNotFound(id)); 
    }

    @Override
    public Usuario editarUsuario(long id, Usuario usuario) throws UsuarioNotFound {
        obtenerUsuario(id); 
        usuario.setId(id);
        return repository.save(usuario); 
    }

    @Override
    public void eliminarUsuario(long id) throws UsuarioNotFound {
        obtenerUsuario(id); 
        repository.deleteById(id);
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        return repository.save(usuario); 
    }
    
}
