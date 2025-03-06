package examen.nmit07e08.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import examen.nmit07e08.config.JwtUtils;
import examen.nmit07e08.config.UserDetailsImpl;
import examen.nmit07e08.domain.Rol;
import examen.nmit07e08.domain.Usuario;
import examen.nmit07e08.domain.dto.security.LoginDto;
import examen.nmit07e08.domain.dto.security.SignupDto;
import examen.nmit07e08.domain.dto.security.UsuarioJwt;
import examen.nmit07e08.repository.UsuarioRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getNombre(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String rol = userDetails.getAuthorities().stream().findFirst().map(a -> a.getAuthority()).orElse("ERROR");
        return ResponseEntity.ok(new UsuarioJwt(jwt, "Bearer",
                userDetails.getId(),
                userDetails.getUsername(),
                rol));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupDto signUpRequest) {
        if (usuarioRepository.existsByDni(signUpRequest.getDni())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Ya existe un usuario con ese nombre");
        }

        // Create new user's account
        Usuario usuario = new Usuario(0,signUpRequest.getDni(),  signUpRequest.getNombre(), signUpRequest.getEmail() ,encoder.encode(signUpRequest.getPassword()), Rol.USUARIO);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuario registrado correctamente ");
    }
}