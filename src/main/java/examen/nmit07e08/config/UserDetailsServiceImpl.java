package examen.nmit07e08.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import examen.nmit07e08.domain.Rol;
import examen.nmit07e08.domain.Usuario;
import examen.nmit07e08.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository; 

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository.findByDni(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado")); 
        return UserDetailsImpl.build(usuario); 
    }

    
    @Transactional
    public void cambiarRol(String username, String nuevoRol) {
        // 1. Buscar el usuario en la base de datos
        Usuario usuario = repository.findByDni(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // 2. Actualizar el rol en la base de datos
        usuario.setRol(Rol.valueOf(nuevoRol)); // Suponiendo que `Rol` es un enum
        repository.save(usuario);

        // 3. Actualizar la autenticación en la sesión
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();

            // 4. Crear una nueva lista de roles con el nuevo rol
            List<GrantedAuthority> updatedAuthorities = List.of(new SimpleGrantedAuthority("ROLE_" + nuevoRol));

            // 5. Crear una nueva autenticación con el nuevo rol
            Authentication newAuth = new UsernamePasswordAuthenticationToken(
                    currentUser,
                    authentication.getCredentials(),
                    updatedAuthorities
            );

            // 6. Reemplazar la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
    }
}
