package examen.nmit07e08.service;

import java.util.List;

import examen.nmit07e08.domain.Usuario;
import examen.nmit07e08.exception.UsuarioNotFound;

public interface UsuarioService {
    List<Usuario> obtenerUsuarios(); 
    Usuario obtenerUsuario(long id) throws UsuarioNotFound; 
    Usuario crearUsuario(Usuario usuario); 
    Usuario editarUsuario(long id, Usuario usuario) throws UsuarioNotFound; 
    void eliminarUsuario(long id) throws UsuarioNotFound; 
}
