package examen.nmit07e08.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import examen.nmit07e08.config.UserDetailsServiceImpl;
import examen.nmit07e08.domain.Cuenta;
import examen.nmit07e08.domain.Rol;
import examen.nmit07e08.domain.Usuario;
import examen.nmit07e08.domain.dto.CuentaDTO;
import examen.nmit07e08.exception.CuentaNotFound;
import examen.nmit07e08.repository.CuentaRepository;
import examen.nmit07e08.repository.UsuarioRepository;

@Service
public class CuentaServiceImpl implements CuentaService{

    @Autowired
    private CuentaRepository repository; 
    @Autowired
    private UsuarioRepository usuarioRepository; 
    @Autowired
    private UserDetailsServiceImpl userDetails; 

    @Autowired
    private ModelMapper modelMapper; 

    @Override
    public List<Cuenta> obtenerCuentas() {
        return repository.findAll(); 
    }

    @Override
    public Cuenta obtenerCuenta(long id) throws CuentaNotFound {
        return repository.findById(id).orElseThrow(() -> new CuentaNotFound(id)); 
    }

    // Cuando creas una cuenta, cambias tu rol ya que pasas a ser titular de esta
    @Override
    public Cuenta crearCuenta(CuentaDTO cuenta) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
        String currentUserRol = authentication.getAuthorities().toString(); 
        if (currentUserRol.equals("[ROLE_USUARIO]")){
            userDetails.cambiarRol(authentication.getName(), Rol.TITULAR.toString()); 
        }  else{
            System.out.println("Hola");
        }

        Cuenta cuenta2 = modelMapper.map(cuenta, Cuenta.class); 
        cuenta2.setSaldo(0);
        Usuario titular = usuarioRepository.findByDni(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        cuenta2.setTitular(titular);
        
        return repository.save(cuenta2); 
    }

    // Los titulares solo podrán borrar y editar su propia cuenta 
    @Override
    public Cuenta editarCuenta(long id, Cuenta cuenta) throws CuentaNotFound {
        Cuenta cuentaBD = obtenerCuenta(id); 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 

        String currentUserRol = authentication.getAuthorities().toString(); 
        if (!currentUserRol.equals("[ROLE_ADMIN]")){
            Usuario usuarioConectado = modelMapper.map((UserDetails) authentication.getPrincipal(), Usuario.class); 
            if (!cuentaBD.getTitular().equals(usuarioConectado)) {
                throw new RuntimeException("No puedes editar una cuenta que no te pertenece"); 
            }
        }
        cuenta.setId(id);
        return repository.save(cuenta); 
    }

    @Override
    public void eliminarCuenta(long id) throws CuentaNotFound {
        Cuenta cuentaBD = obtenerCuenta(id); 

        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
        String currentUserRol = authentication.getAuthorities().toString(); 
        if (!currentUserRol.equals("[ROLE_ADMIN]")){
            Usuario usuarioConectado = modelMapper.map((UserDetails) authentication.getPrincipal(), Usuario.class); 
            if (!cuentaBD.getTitular().equals(usuarioConectado)) {
                throw new RuntimeException("No puedes borrar una cuenta que no te pertenece"); 
            }
        }

        
        repository.deleteById(id);
    }
    
}
