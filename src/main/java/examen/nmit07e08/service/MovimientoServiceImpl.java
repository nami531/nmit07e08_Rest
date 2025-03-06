package examen.nmit07e08.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import examen.nmit07e08.domain.Cuenta;
import examen.nmit07e08.domain.Movimiento;
import examen.nmit07e08.domain.Usuario;
import examen.nmit07e08.domain.dto.MovimientoDTO;
import examen.nmit07e08.exception.CuentaNotFound;
import examen.nmit07e08.exception.MovimientoNotFound;
import examen.nmit07e08.repository.CuentaRepository;
import examen.nmit07e08.repository.MovimientoRepository;

@Service
public class MovimientoServiceImpl implements MovimientoService{

    @Autowired
    private MovimientoRepository repository; 

    @Autowired
    private CuentaRepository cuentaRepository; 

    @Autowired 
    private ModelMapper modelMapper; 

    @Override
    public List<Movimiento> obtenerMovimientos(){
        return repository.findAll(); 
    }

    @Override
    public Movimiento obtenerMovimiento(long id) throws MovimientoNotFound {
        return repository.findById(id).orElseThrow(() -> new MovimientoNotFound(id)); 
    }

    @Override
    public Movimiento crearMovimiento(MovimientoDTO movDTO) {

        Movimiento movimiento = modelMapper.map(movDTO, Movimiento.class); 

        Cuenta cuenta = movimiento.getCuenta(); 

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
        String currentUserRol = authentication.getAuthorities().toString(); 
        if (!currentUserRol.equals("[ROLE_ADMIN]")){
            Usuario usuarioConectado = modelMapper.map((UserDetails) authentication.getPrincipal(), Usuario.class); 
            if (!cuenta.getTitular().equals(usuarioConectado)) {
                throw new RuntimeException("No puedes crear un movimiento en una cuenta que no te pertenece"); 
            }
        }


        cuenta.setSaldo(movimiento.getCuenta().getSaldo() + movimiento.getImporte());
        cuentaRepository.save(cuenta);
        
        movimiento.setFecha(LocalDateTime.now());
        return repository.save(movimiento); 
    }

    @Override
    public Movimiento editarMovimiento(long id, MovimientoDTO movDTO) throws MovimientoNotFound {
        Movimiento movBD = obtenerMovimiento(id); 
        Movimiento movimiento = modelMapper.map(movDTO, Movimiento.class); 


        Cuenta cuenta = movimiento.getCuenta(); 

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
        String currentUserRol = authentication.getAuthorities().toString(); 
        if (!currentUserRol.equals("[ROLE_ADMIN]")){
            Usuario usuarioConectado = modelMapper.map((UserDetails) authentication.getPrincipal(), Usuario.class); 
            if (!cuenta.getTitular().equals(usuarioConectado)) {
                throw new RuntimeException("No puedes editar un movimiento en una cuenta que no te pertenece"); 
            }
        }



        cuenta.setSaldo(movimiento.getCuenta().getSaldo() - movBD.getImporte() + movimiento.getImporte());
        cuentaRepository.save(cuenta);

        movimiento.setFecha(LocalDateTime.now());

        movimiento.setId(id);
        return repository.save(movimiento); 
    }

    @Override
    public void eliminarMovimiento(long id) throws MovimientoNotFound {
        obtenerMovimiento(id); 
        repository.deleteById(id);
    }
    // TODO: Meter el throw exception dentro de un if usuarioConectado != admin
    @Override
    public List<Movimiento> obtenerMovimientosCuenta(long idCuenta) throws CuentaNotFound {
        Cuenta cuenta = cuentaRepository.findById(idCuenta).orElseThrow(() -> new CuentaNotFound(idCuenta));
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
        String currentUserRol = authentication.getAuthorities().toString(); 
        if (!currentUserRol.equals("[ROLE_ADMIN]")){
            Usuario usuarioConectado = modelMapper.map((UserDetails) authentication.getPrincipal(), Usuario.class); 
            if (!cuenta.getTitular().equals(usuarioConectado)) {
                throw new RuntimeException("No puedes eliminar un movimiento en una cuenta que no te pertenece"); 
            }
        }
        return repository.findByCuenta(cuenta); 
    }
    
}
